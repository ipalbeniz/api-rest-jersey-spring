package com.demo.api.rest.error.mapping;

import com.demo.api.rest.error.ApiErrorCatalog;
import com.demo.api.rest.model.error.ApiErrorMessage;

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
        //setStackTrace(exception, errorMessage);
        
        return Response.status(errorMessage.getStatus())
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private void setStackTrace(Throwable exception, ApiErrorMessage errorMessage) {
        
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