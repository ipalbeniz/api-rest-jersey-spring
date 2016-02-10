package com.barcelo.api.rest.demo.error;


import com.barcelo.api.rest.demo.model.error.ApiErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AppExceptionMapper implements ExceptionMapper<AppException> {

    @Override
    public Response toResponse(AppException appException) {

        ApiErrorMessage apiErrorMessage = ApiErrorMessage.valueOf(appException.getApiErrorCatalog());

        return Response.status(appException.getApiErrorCatalog().getHttpStatus())
                .entity(apiErrorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
