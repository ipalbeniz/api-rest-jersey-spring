package com.demo.api.rest.error.mapping;


import com.demo.api.rest.error.AppException;
import com.demo.api.rest.model.error.ApiErrorMessage;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AppExceptionMapper extends AbstractExceptionMapper<AppException> implements ExceptionMapper<AppException> {

    @Override
    protected ApiErrorMessage getApiErrorMessage(AppException exception) {
        return ApiErrorMessage.valueOf(exception.getAppErrorCatalog());
    }

}
