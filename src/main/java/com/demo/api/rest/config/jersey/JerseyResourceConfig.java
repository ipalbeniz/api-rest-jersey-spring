package com.demo.api.rest.config.jersey;

import com.demo.api.rest.error.mapping.JsonParseExceptionMapper;
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

        // Swagger
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        // Features
        register(JacksonFeature.class);

        // This has to be registered after JacksonFeature in order to override the default JsonParseExceptionMapper
        register(JsonParseExceptionMapper.class);
    }

}