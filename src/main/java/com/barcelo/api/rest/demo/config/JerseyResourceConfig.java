package com.barcelo.api.rest.demo.config;

import com.barcelo.api.rest.demo.error.AppExceptionMapper;
import com.barcelo.api.rest.demo.error.ConstraintViolationExceptionMapper;
import com.barcelo.api.rest.demo.error.GenericExceptionMapper;
import com.barcelo.api.rest.demo.error.NotFoundExceptionMapper;
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

        // Exception Mappers
        register(AppExceptionMapper.class);
        register(NotFoundExceptionMapper.class);
        register(ConstraintViolationExceptionMapper.class);
        register(GenericExceptionMapper.class);

        register(ValidationConfigurationContextResolver.class);

        // Features
        register(JacksonFeature.class);
        EncodingFilter.enableFor(this, GZipEncoder.class);
    }
}