package com.demo.api.rest.config.jersey.Interceptors;

import com.demo.api.rest.config.jersey.annotations.GZip;
import org.apache.commons.io.output.TeeOutputStream;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

@Provider
@Priority(Priorities.ENTITY_CODER)
@GZip
public class GZIPWriterInterceptor implements ContainerResponseFilter, WriterInterceptor {

    public static final String STREAM_WITHOUT_GZIP_PROPERTY = "streamWithoutGzip";
    public static final String GZIP = "gzip";

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        if (clientAcceptsGzipEncoding(containerRequestContext)) {
            containerResponseContext.getHeaders().add(HttpHeaders.CONTENT_ENCODING, GZIP);
        }
    }

    private boolean clientAcceptsGzipEncoding(ContainerRequestContext containerRequestContext) {
        MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();

        if (headers != null && headers.get(HttpHeaders.ACCEPT_ENCODING) != null) {
            for (String headerEncoding : headers.get(HttpHeaders.ACCEPT_ENCODING)) {
                if (StringUtils.contains(headerEncoding, GZIP)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {

        if (context.getHeaders() != null
                && context.getHeaders().containsKey(HttpHeaders.CONTENT_ENCODING)
                && context.getHeaders().get(HttpHeaders.CONTENT_ENCODING).contains(GZIP)) {

            OutputStream outputStream = context.getOutputStream();

            ByteArrayOutputStream otherOutputStream = new ByteArrayOutputStream();
            TeeOutputStream teeOutputStream = new TeeOutputStream(new GZIPOutputStream(outputStream), otherOutputStream);

            context.setProperty(STREAM_WITHOUT_GZIP_PROPERTY, otherOutputStream);

            context.setOutputStream(teeOutputStream);
        }

        context.proceed();
    }
}