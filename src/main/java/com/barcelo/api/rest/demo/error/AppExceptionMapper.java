package com.barcelo.api.rest.demo.error;


import com.barcelo.api.rest.demo.model.ApiErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AppExceptionMapper implements ExceptionMapper<AppException> {

    @Override
    public Response toResponse(AppException appException) {

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage();

        apiErrorMessage.setStatus(appException.getApiError().getHttpStatus().getStatusCode());
        apiErrorMessage.setCode(appException.getApiError().getCode());
        apiErrorMessage.setMessage(appException.getApiError().getMessage());
        apiErrorMessage.setDeveloperMessage(appException.getApiError().getDeveloperMessage());

        return Response.status(appException.getApiError().getHttpStatus())
                .entity(apiErrorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
