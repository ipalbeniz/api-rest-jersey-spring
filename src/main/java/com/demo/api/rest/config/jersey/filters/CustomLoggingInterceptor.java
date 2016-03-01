package com.demo.api.rest.config.jersey.filters;

import com.demo.api.rest.config.jersey.annotations.Log;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.TeeOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Provider
@Priority(Priorities.USER)
@Log
public class CustomLoggingInterceptor implements ContainerRequestFilter, ContainerResponseFilter, WriterInterceptor {

    private static final String ENTITY_LOGGER_PROPERTY = "entityLogger";

    @Context
    private ResourceInfo resourceInfo;

    private static final Logger log = LoggerFactory.getLogger(CustomLoggingInterceptor.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        log.debug("Path : {} ", requestContext.getUriInfo().getPath());
        log.debug("Method : {}", requestContext.getMethod());
        log.debug("MediaType : {}", requestContext.getMediaType());
        log.debug("Resource : {}.{} ", resourceInfo.getResourceClass().getCanonicalName(), resourceInfo.getResourceMethod().getName());

        logRequestHeaders(requestContext.getHeaders());
        logQueryParameters(requestContext.getUriInfo().getQueryParameters());

        if (requestContext.hasEntity()) {
            log.debug("Request : {}", getRequestEntityStream(requestContext));
        }

    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {

        ByteArrayOutputStream stream = (ByteArrayOutputStream) context.getProperty(GZIPWriterInterceptor.STREAM_WITHOUT_GZIP_PROPERTY);

        if ( stream == null) {
            stream = new ByteArrayOutputStream();
            context.setOutputStream(new TeeOutputStream(context.getOutputStream(), stream) );
        }
        context.proceed();

        logResponseHeaders(context.getHeaders());

        log.debug("Response MediaType : {}", context.getMediaType());

        if (stream != null) {
            log.debug("Response : [{}]", new String(stream.toByteArray()));
        }

    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        log.debug("Response status : {}", responseContext.getStatus());
    }

    private String getRequestEntityStream(ContainerRequestContext requestContext) throws IOException {

        byte [] requestEntityBytes = IOUtils.toByteArray(requestContext.getEntityStream());
        requestContext.setEntityStream(new ByteArrayInputStream(requestEntityBytes));

        return new String(requestEntityBytes);
    }

    private void logQueryParameters(MultivaluedMap<String, String> queryParameters) {
        for (Map.Entry<String, List<String>> queryParameter : queryParameters.entrySet()) {
            log.debug("Query Parameter: {} = {}", queryParameter.getKey(), StringUtils.join(queryParameter.getValue(),","));
        }
    }

    private void logRequestHeaders(MultivaluedMap<String, String> headers) {
        for (Map.Entry<String, List<String>> header : headers.entrySet()) {
            log.debug("Request Header: {} = {} ", header.getKey(), StringUtils.join(header.getValue(),","));
        }
    }

    private void logResponseHeaders(MultivaluedMap<String, Object> headers) {
        for (Map.Entry<String, List<Object>> header : headers.entrySet()) {
            log.debug("Response Header: {} = {} ", header.getKey(), StringUtils.join(header.getValue(),","));
        }
    }
}