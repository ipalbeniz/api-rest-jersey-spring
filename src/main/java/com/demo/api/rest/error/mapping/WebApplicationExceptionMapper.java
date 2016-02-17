package com.demo.api.rest.error.mapping;


import com.demo.api.rest.error.AppErrorCatalog;
import com.demo.api.rest.model.error.ApiErrorMessage;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper extends AbstractExceptionMapper<WebApplicationException> implements ExceptionMapper<WebApplicationException> {

    @Override
    protected ApiErrorMessage getApiErrorMessage(WebApplicationException exception) {
        ApiErrorMessage apiErrorMessage = ApiErrorMessage.valueOf(AppErrorCatalog.GENERIC_ERROR);

        apiErrorMessage.setStatus(exception.getResponse().getStatus());

        return apiErrorMessage;
    }

}
