package com.barcelo.api.rest.demo.error;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.io.PrintWriter;
import java.io.StringWriter;

public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {

        ApiErrorMessage errorMessage = new ApiErrorMessage();

        setHttpStatus(ex, errorMessage);
        errorMessage.setCode(AppException.ApiError.GENERIC_ERROR.getCode());
        errorMessage.setMessage(AppException.ApiError.GENERIC_ERROR.getMessage());

        StringWriter errorStackTrace = new StringWriter();
        ex.printStackTrace(new PrintWriter(errorStackTrace));
        errorMessage.setDeveloperMessage(errorStackTrace.toString());

        return Response.status(errorMessage.getStatus())
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private void setHttpStatus(Throwable ex, ApiErrorMessage errorMessage) {
        if (ex instanceof WebApplicationException) {
            errorMessage.setStatus(((WebApplicationException) ex).getResponse().getStatus());
        } else {
            errorMessage.setStatus(AppException.ApiError.GENERIC_ERROR.getHttpStatus().getStatusCode());
        }
    }
}