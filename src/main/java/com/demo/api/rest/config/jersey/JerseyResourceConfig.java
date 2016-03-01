package com.demo.api.rest.config.jersey;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.web.filter.RequestContextFilter;

public class JerseyResourceConfig extends ResourceConfig {

    public JerseyResourceConfig() {

        // Filters
        register(RequestContextFilter.class);

        // Properties
        property(ServerProperties.MONITORING_STATISTICS_ENABLED, true);

        // Resources
        packages("com.demo.api.rest");

        // Features
        register(JacksonFeature.class);
    }

}