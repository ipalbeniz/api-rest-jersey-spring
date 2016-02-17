package com.demo.api.rest.error.mapping;


import com.demo.api.rest.error.AppErrorCatalog;
import com.demo.api.rest.model.error.ApiErrorMessage;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper extends AbstractExceptionMapper<NotFoundException> implements ExceptionMapper<NotFoundException> {

    @Override
    protected ApiErrorMessage getApiErrorMessage(NotFoundException exception) {
            return ApiErrorMessage.valueOf(AppErrorCatalog.NOT_FOUND_ERROR);
    }

}
