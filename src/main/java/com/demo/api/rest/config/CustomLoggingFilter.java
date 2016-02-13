package com.demo.api.rest.config;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.TeeOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Provider
public class CustomLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter, WriterInterceptor {

    private static final int DEFAULT_MAX_ENTITY_SIZE = 8 * 1024;
    private static final String ENTITY_LOGGER_PROPERTY = CustomLoggingFilter.class.getName() + ".entityLogger";

    @Context
    private ResourceInfo resourceInfo;

    private static final Logger log = LoggerFactory.getLogger(CustomLoggingFilter.class);

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
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        logResponseHeaders(responseContext.getHeaders());

        log.debug("Response MediaType : {}", responseContext.getMediaType());

        log.debug("Response status : {}", responseContext.getStatus());

        if (responseContext.hasEntity()) {
            ByteArrayOutputStream otherOutputStream = new ByteArrayOutputStream();
            TeeOutputStream teeOutputStream = new TeeOutputStream(responseContext.getEntityStream(), otherOutputStream);

            responseContext.setEntityStream(teeOutputStream);
            requestContext.setProperty(ENTITY_LOGGER_PROPERTY, otherOutputStream);
        }

    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext) throws IOException, WebApplicationException {
        ByteArrayOutputStream stream = (ByteArrayOutputStream) writerInterceptorContext.getProperty(ENTITY_LOGGER_PROPERTY);
        writerInterceptorContext.proceed();
        if (stream != null) {
            log.debug("Response : [{}]", new String(stream.toByteArray()));
        }

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
            log.debug("Header: {} = {} ", header.getKey(), StringUtils.join(header.getValue(),","));
        }
    }

    private void logResponseHeaders(MultivaluedMap<String, Object> headers) {
        for (Map.Entry<String, List<Object>> header : headers.entrySet()) {
            log.debug("Header: {} = {} ", header.getKey(), StringUtils.join(header.getValue(),","));
        }
    }

    private class LoggingStream extends OutputStream {

        private final StringBuilder stringBuilder;
        private final OutputStream inner;
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        LoggingStream(final StringBuilder stringBuilder, final OutputStream inner) {
            this.stringBuilder = stringBuilder;
            this.inner = inner;
        }

        StringBuilder getStringBuilder() {
            // write entity to the builder
            final byte[] entity = baos.toByteArray();

            stringBuilder.append(new String(entity, 0, Math.min(entity.length, DEFAULT_MAX_ENTITY_SIZE)));
            if (entity.length > DEFAULT_MAX_ENTITY_SIZE) {
                stringBuilder.append("...more...");
            }
            stringBuilder.append('\n');

            return stringBuilder;
        }

        @Override
        public void write(final int i) throws IOException {
            if (baos.size() <= DEFAULT_MAX_ENTITY_SIZE) {
                baos.write(i);
            }
            inner.write(i);
        }
    }
}