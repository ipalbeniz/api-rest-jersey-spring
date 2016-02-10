package com.barcelo.api.rest.demo.config;

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
        packages("com.barcelo.api.rest.demo");

        // Features
        register(JacksonFeature.class);
        EncodingFilter.enableFor(this, GZipEncoder.class);
    }
}