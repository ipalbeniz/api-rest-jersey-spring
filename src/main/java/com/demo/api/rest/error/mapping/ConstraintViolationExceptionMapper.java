package com.demo.api.rest.error.mapping;

import com.demo.api.rest.error.AppErrorCatalog;
import com.demo.api.rest.model.error.ApiErrorMessage;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Iterator;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<javax.validation.ConstraintViolationException> {

    @Override
    public Response toResponse(javax.validation.ConstraintViolationException exception) {
        ApiErrorMessage apiErrorMessage = ApiErrorMessage.valueOf(AppErrorCatalog.VALIDATION_ERROR);

        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            Path.Node lastNode = getLastNodePath(constraintViolation.getPropertyPath().iterator());

            apiErrorMessage.appendConstraintError(lastNode.getName(), constraintViolation.getMessage());
        }

        return Response.status(apiErrorMessage.getStatus())
                .entity(apiErrorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private static Path.Node getLastNodePath(Iterator<Path.Node> iterator) {
        Path.Node lastElement = iterator.next();

        while (iterator.hasNext()) {
            lastElement = iterator.next();
        }

        return lastElement;
    }
}
