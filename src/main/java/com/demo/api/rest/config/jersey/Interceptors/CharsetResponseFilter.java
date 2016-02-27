package com.demo.api.rest.config.jersey.Interceptors;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class CharsetResponseFilter implements ContainerResponseFilter {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CHARSET_UTF_8 = "charset=UTF-8";
    public static final String SEPARATOR = ";";

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        MediaType contentType = containerResponseContext.getMediaType();
        if (contentType != null) {
            containerResponseContext.getHeaders().putSingle(CONTENT_TYPE, contentType.toString() + SEPARATOR + CHARSET_UTF_8);
        }
    }
}