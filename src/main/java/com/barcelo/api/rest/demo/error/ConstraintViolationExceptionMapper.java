package com.barcelo.api.rest.demo.error;

import com.barcelo.api.rest.demo.model.ApiErrorMessage;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.Iterator;


public class ConstraintViolationExceptionMapper implements ExceptionMapper<javax.validation.ConstraintViolationException> {

    @Override
    public Response toResponse(javax.validation.ConstraintViolationException exception) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage();

        StringBuilder errorMsg = new StringBuilder();
        
        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            Path.Node lastNode = getLastNodePath(constraintViolation.getPropertyPath().iterator());
            if (!errorMsg.toString().isEmpty()) {
                errorMsg.append("\\n");
            }
            errorMsg.append(lastNode.getName()).append(' ').append(constraintViolation.getMessage());
        }

        apiErrorMessage.setCode(99);
        apiErrorMessage.setMessage("Validation Error");
        apiErrorMessage.setDeveloperMessage(errorMsg.toString());
        apiErrorMessage.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
        
        return Response.status(apiErrorMessage.getStatus())
                .entity(apiErrorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private static Path.Node getLastNodePath(Iterator<Path.Node> iterator) {
        Path.Node lastElement = iterator.next();
        while(iterator.hasNext()) {
            lastElement = iterator.next();
        }

        return lastElement;
    }
}
