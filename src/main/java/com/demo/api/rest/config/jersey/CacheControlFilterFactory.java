package com.demo.api.rest.config.jersey;

import com.demo.api.rest.config.jersey.annotations.CacheControlMaxAge;
import com.demo.api.rest.config.jersey.annotations.CacheControlNoCache;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.*;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;

@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class CacheControlFilterFactory implements DynamicFeature {

    private static final CacheResponseFilter NO_CACHE_FILTER = new CacheResponseFilter("no-cache");

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {

        Method method = resourceInfo.getResourceMethod();

        if (method.isAnnotationPresent(CacheControlMaxAge.class)) {

            CacheControlMaxAge maxAge = method.getAnnotation(CacheControlMaxAge.class);
            featureContext.register(new CacheResponseFilter("max-age=" + maxAge.unit().toSeconds(maxAge.time())));

        } else if (method.isAnnotationPresent(CacheControlNoCache.class)) {

            featureContext.register(NO_CACHE_FILTER);
        }
    }

    private static class CacheResponseFilter implements ContainerResponseFilter {

        private final String headerValue;

        CacheResponseFilter(String headerValue) {
            this.headerValue = headerValue;
        }

        @Override
        public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) {
            containerResponseContext
                    .getHeaders()
                    .putSingle(HttpHeaders.CACHE_CONTROL, headerValue);
        }

    }
}