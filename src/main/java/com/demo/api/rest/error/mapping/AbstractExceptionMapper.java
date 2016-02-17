package com.demo.api.rest.error.mapping;


import com.demo.api.rest.model.error.ApiErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public abstract class AbstractExceptionMapper<T extends Throwable> implements ExceptionMapper<T> {

    @Override
    public Response toResponse(T exception) {

        ApiErrorMessage apiErrorMessage = getApiErrorMessage(exception);

        return Response.status(apiErrorMessage.getStatus())
                .entity(apiErrorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    protected abstract ApiErrorMessage getApiErrorMessage(T exception);
}
