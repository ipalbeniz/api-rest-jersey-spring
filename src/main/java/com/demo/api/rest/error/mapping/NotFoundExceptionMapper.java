package com.demo.api.rest.error.mapping;


import com.demo.api.rest.error.ApiErrorCatalog;
import com.demo.api.rest.model.error.ApiErrorMessage;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {

        ApiErrorMessage errorMessage = ApiErrorMessage.valueOf(ApiErrorCatalog.NOT_FOUND_ERROR);

        return Response.status(errorMessage.getStatus())
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
