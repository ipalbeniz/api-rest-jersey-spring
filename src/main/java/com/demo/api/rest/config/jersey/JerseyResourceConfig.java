package com.demo.api.rest.config.jersey;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.EncodingFilter;
import org.springframework.web.filter.RequestContextFilter;

public class JerseyResourceConfig extends ResourceConfig {

    public JerseyResourceConfig() {

        // Filters
        register(RequestContextFilter.class);

        // Resources
        packages("com.demo.api.rest");

        // Features
        register(JacksonFeature.class);
        register(ResponseCorsFilter.class);
        EncodingFilter.enableFor(this, GZipEncoder.class);
    }
}