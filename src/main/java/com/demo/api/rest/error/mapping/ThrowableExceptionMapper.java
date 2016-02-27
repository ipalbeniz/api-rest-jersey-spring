package com.demo.api.rest.error.mapping;

import com.demo.api.rest.error.AppErrorCatalog;
import com.demo.api.rest.model.error.ApiErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ThrowableExceptionMapper extends AbstractExceptionMapper<Throwable> implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThrowableExceptionMapper.class);

    @Override
    protected ApiErrorMessage getApiErrorMessage(Throwable exception) {
        LOGGER.error("Unexpected exception", exception);
        return ApiErrorMessage.valueOf(AppErrorCatalog.GENERIC_ERROR);
    }

}