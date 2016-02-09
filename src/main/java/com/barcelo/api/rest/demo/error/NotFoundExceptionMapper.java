package com.barcelo.api.rest.demo.error;


import com.barcelo.api.rest.demo.model.ApiErrorMessage;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {

        ApiErrorMessage errorMessage = ApiErrorMessage.valueOf(ApiError.NOT_FOUND_ERROR);

        return Response.status(errorMessage.getStatus())
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
