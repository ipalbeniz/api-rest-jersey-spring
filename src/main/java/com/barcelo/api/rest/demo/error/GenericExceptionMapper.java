package com.barcelo.api.rest.demo.error;

import com.barcelo.api.rest.demo.model.error.ApiErrorMessage;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.PrintWriter;
import java.io.StringWriter;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {

        ApiErrorMessage errorMessage = ApiErrorMessage.valueOf(ApiErrorCatalog.GENERIC_ERROR);

        setHttpStatus(exception, errorMessage);
        setDeveloperMessage(exception, errorMessage);
        
        return Response.status(errorMessage.getStatus())
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private void setDeveloperMessage(Throwable exception, ApiErrorMessage errorMessage) {
        
        StringWriter errorStackTrace = new StringWriter();
        
        exception.printStackTrace(new PrintWriter(errorStackTrace));
        
        errorMessage.setDeveloperMessage(errorStackTrace.toString());
    }

    private void setHttpStatus(Throwable ex, ApiErrorMessage errorMessage) {
        
        if (ex instanceof WebApplicationException) {
            errorMessage.setStatus(((WebApplicationException) ex).getResponse().getStatus());
        }
    }
}