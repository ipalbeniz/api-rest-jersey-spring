package com.demo.api.rest.error.mapping;

import com.demo.api.rest.error.AppErrorCatalog;
import com.demo.api.rest.model.error.ApiErrorMessage;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ThrowableExceptionMapper extends AbstractExceptionMapper<Throwable> implements ExceptionMapper<Throwable> {

    @Override
    protected ApiErrorMessage getApiErrorMessage(Throwable exception) {
        return ApiErrorMessage.valueOf(AppErrorCatalog.GENERIC_ERROR);
    }

}