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
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;

@Provider
@Priority(Priorities.ENTITY_CODER)
@GZip
public class GZIPWriterInterceptor implements ContainerResponseFilter, WriterInterceptor {

    public static final String STREAM_WITHOUT_GZIP_PROPERTY = "streamWithoutGzip";
    private static final List<String> ACCEPTED_GZIP_ENCODINGS = Arrays.asList("gzip", "x-gzip");
    private static final String GZIP_CONTENT_ENCODING = "gzip";

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        if (clientAcceptsGzipEncoding(containerRequestContext)) {
            containerResponseContext.getHeaders().add(HttpHeaders.CONTENT_ENCODING, GZIP_CONTENT_ENCODING);
        }
    }

    private boolean clientAcceptsGzipEncoding(ContainerRequestContext containerRequestContext) {
        MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();

        if (headers != null && headers.get(HttpHeaders.ACCEPT_ENCODING) != null) {
            String headerEncoding = headers.getFirst(HttpHeaders.ACCEPT_ENCODING);
            for (String gzipEncoding : ACCEPTED_GZIP_ENCODINGS) {
                if (StringUtils.contains(headerEncoding, gzipEncoding)) {
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
                && context.getHeaders().get(HttpHeaders.CONTENT_ENCODING).contains(GZIP_CONTENT_ENCODING)) {

            OutputStream originalStream = context.getOutputStream();

            ByteArrayOutputStream streamWithoutGzip = new ByteArrayOutputStream();
            TeeOutputStream teeOutputStream = new TeeOutputStream(new GZIPOutputStream(originalStream), streamWithoutGzip);

            context.setProperty(STREAM_WITHOUT_GZIP_PROPERTY, streamWithoutGzip);

            context.setOutputStream(teeOutputStream);
        }

        context.proceed();
    }
}