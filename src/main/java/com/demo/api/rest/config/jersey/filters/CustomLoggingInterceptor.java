package com.demo.api.rest.config.jersey.filters;

import com.demo.api.rest.config.jersey.annotations.Log;
import com.demo.api.rest.model.log.ApiLog;
import com.demo.api.rest.model.log.ApiLogRequest;
import com.demo.api.rest.model.log.ApiLogResponse;
import com.demo.api.rest.repository.ApiLogRespository;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.TeeOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
@Provider
@Priority(Priorities.USER)
@Component
public class CustomLoggingInterceptor implements ContainerRequestFilter, ContainerResponseFilter, WriterInterceptor {

    public static final String API_LOG_REQUEST_PROPERTY = "apiLogRequest";
    public static final String API_LOG_RESPONSE_PROPERTY = "apiLogResponse";

    @Autowired
    private ApiLogRespository apiLogRespository;

    @Value("${log.mongo.active}")
    private boolean logMongoActive;

    @Context
    private ResourceInfo resourceInfo;

    private static final Logger log = LoggerFactory.getLogger(CustomLoggingInterceptor.class);

    @Override
    public void filter(ContainerRequestContext requestContext) {

        ApiLogRequest apiLogRequest = createApiLogRequest(requestContext);
        requestContext.setProperty(API_LOG_REQUEST_PROPERTY, apiLogRequest);

        logApiRequest(apiLogRequest);
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {

        ApiLogResponse apiLogResponse = createApiLogResponse(requestContext, responseContext);
        requestContext.setProperty(API_LOG_RESPONSE_PROPERTY, apiLogResponse);
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws WebApplicationException, IOException {

        ByteArrayOutputStream responseEntity = getResponseEntity(context);

        // Continue with the other interceptors in order to get the responseEntity content
        context.proceed();

        ApiLogResponse apiLogResponse = (ApiLogResponse) context.getProperty(API_LOG_RESPONSE_PROPERTY);

        String responseEntityString = new String(responseEntity.toByteArray());
        if (StringUtils.isNotEmpty(responseEntityString)) {
            try {
                apiLogResponse.setEntity((DBObject) JSON.parse(responseEntityString));
            } catch (JSONParseException e) {
                apiLogResponse.setUnparsedEntity(responseEntityString);
            }
        }

        logApiResponse(apiLogResponse, responseEntityString);

        if (logMongoActive) {
            saveApiLog(context);
        }
    }

    private void saveApiLog(WriterInterceptorContext context) {

        ApiLogRequest apiLogRequest = (ApiLogRequest) context.getProperty(API_LOG_REQUEST_PROPERTY);
        ApiLogResponse apiLogResponse = (ApiLogResponse) context.getProperty(API_LOG_RESPONSE_PROPERTY);

        ApiLog apiLog = new ApiLog();

        apiLog.setRequest(apiLogRequest);
        apiLog.setResponse(apiLogResponse);
        if (apiLogRequest != null && apiLogResponse != null) {
            apiLog.setProcessTime(apiLogResponse.getDateTime().getTime() - apiLogRequest.getDateTime().getTime());
        }

        apiLogRespository.save(apiLog);
    }

    private ApiLogRequest createApiLogRequest(ContainerRequestContext requestContext) {

        ApiLogRequest apiLogRequest = new ApiLogRequest();

        apiLogRequest.setDateTime(new Date());
        apiLogRequest.setPath(requestContext.getUriInfo().getPath());
        apiLogRequest.setMethod(requestContext.getMethod());
        if (requestContext.getMediaType() != null) {
            apiLogRequest.setMediaType(requestContext.getMediaType().toString());
        }

        // Headers
        if (!CollectionUtils.isEmpty(requestContext.getHeaders())) {
            Map<String, String> headers = new HashMap<>();
            for (Map.Entry<String, List<String>> header : requestContext.getHeaders().entrySet()) {
                headers.put(header.getKey(), StringUtils.join(header.getValue(), ","));
            }
            apiLogRequest.setHeaders(headers);
        }

        // QueryParameters
        if (requestContext.getUriInfo() != null && !CollectionUtils.isEmpty(requestContext.getUriInfo().getQueryParameters())) {
            Map<String, String> queryParameters = new HashMap<>();
            for (Map.Entry<String, List<String>> queryParam : requestContext.getUriInfo().getQueryParameters().entrySet()) {
                queryParameters.put(queryParam.getKey(), StringUtils.join(queryParam.getValue(), ","));
            }
            apiLogRequest.setQueryParameters(queryParameters);
        }

        if (requestContext.hasEntity()) {
            String requestEntityString = getRequestEntityString(requestContext);
            try {
                apiLogRequest.setEntity((DBObject) JSON.parse(requestEntityString));
            } catch (JSONParseException e) {
                apiLogRequest.setUnparsedEntity(requestEntityString);
            }
        }

        return apiLogRequest;
    }

    private void logApiRequest(ApiLogRequest apiLogRequest) {

        log.debug("Request Path : {} ", apiLogRequest.getPath());
        log.debug("Request Method : {}", apiLogRequest.getMethod());
        log.debug("Request MediaType : {}", apiLogRequest.getMediaType());

        if (!CollectionUtils.isEmpty(apiLogRequest.getHeaders())) {
            for (Map.Entry<String, String> header : apiLogRequest.getHeaders().entrySet()) {
                log.debug("Request Header: {} = {} ", header.getKey(), header.getValue());
            }
        }

        if (!CollectionUtils.isEmpty(apiLogRequest.getQueryParameters())) {
            for (Map.Entry<String, String> queryParam : apiLogRequest.getQueryParameters().entrySet()) {
                log.debug("Request Query Param: {} = {} ", queryParam.getKey(), queryParam.getValue());
            }
        }

        if (apiLogRequest.getEntity() != null) {
            log.debug("Request: {}", apiLogRequest.getEntity());
        } else if (StringUtils.isNotEmpty(apiLogRequest.getUnparsedEntity())) {
            log.debug("Request: {}", apiLogRequest.getUnparsedEntity());
        }
    }

    private void logApiResponse(ApiLogResponse apiLogResponse, String responseEntityString) {

        log.debug("Response MediaType: {}", apiLogResponse.getMediaType());
        log.debug("Response status: {}", apiLogResponse.getHttpStatusCode());

        if (!CollectionUtils.isEmpty(apiLogResponse.getHeaders())) {
            for (Map.Entry<String, String> header : apiLogResponse.getHeaders().entrySet()) {
                log.debug("Response Header: {} = {} ", header.getKey(), header.getValue());
            }
        }

        if (StringUtils.isNotEmpty(responseEntityString)) {
            log.debug("Response: {}", responseEntityString);
        }
    }

    private ByteArrayOutputStream getResponseEntity(WriterInterceptorContext context) {

        ByteArrayOutputStream stream = (ByteArrayOutputStream) context.getProperty(GZIPWriterInterceptor.STREAM_WITHOUT_GZIP_PROPERTY);

        if ( stream == null) {
            stream = new ByteArrayOutputStream();
            context.setOutputStream(new TeeOutputStream(context.getOutputStream(), stream) );
        }

        return stream;
    }

    private ApiLogResponse createApiLogResponse(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {

        ApiLogResponse apiLogResponse = new ApiLogResponse();

        apiLogResponse.setDateTime(new Date());
        if (responseContext.getMediaType() != null) {
            apiLogResponse.setMediaType(responseContext.getMediaType().toString());
        }
        apiLogResponse.setHttpStatusCode(responseContext.getStatus());

        Map<String, String> headers = new HashMap<>();
        for (Map.Entry<String, List<Object>> header : responseContext.getHeaders().entrySet()) {
            headers.put(header.getKey(), StringUtils.join(header.getValue(),","));
        }
        apiLogResponse.setHeaders(headers);

        return apiLogResponse;
    }

    private String getRequestEntityString(ContainerRequestContext requestContext) {

        try {
            byte [] requestEntityBytes = new byte[0];

            requestEntityBytes = IOUtils.toByteArray(requestContext.getEntityStream());
            requestContext.setEntityStream(new ByteArrayInputStream(requestEntityBytes));

            return new String(requestEntityBytes);
        } catch (IOException e) {
            log.error("Error reading request entity", e);
            return null;
        }
    }
}