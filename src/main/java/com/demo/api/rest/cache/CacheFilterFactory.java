package com.demo.api.rest.cache;

import com.demo.api.rest.cache.annotations.CacheMaxAge;
import com.demo.api.rest.cache.annotations.NoCache;

import javax.ws.rs.container.*;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;

@Provider
public class CacheFilterFactory implements DynamicFeature {

    private static final CacheResponseFilter NO_CACHE_FILTER = new CacheResponseFilter("no-cache");

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {

        Method method = resourceInfo.getResourceMethod();

        if (method.isAnnotationPresent(CacheMaxAge.class)) {

            CacheMaxAge maxAge = method.getAnnotation(CacheMaxAge.class);
            featureContext.register(new CacheResponseFilter("max-age=" + maxAge.unit().toSeconds(maxAge.time())));

        } else if (method.isAnnotationPresent(NoCache.class)) {

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