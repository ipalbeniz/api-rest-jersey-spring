package com.demo.api.rest.error.mapping;

import com.demo.api.rest.error.AppErrorCatalog;
import com.demo.api.rest.model.error.ApiErrorMessage;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Iterator;

@Provider
public class ConstraintViolationExceptionMapper extends AbstractExceptionMapper<ConstraintViolationException> implements ExceptionMapper<ConstraintViolationException> {

    @Override
    protected ApiErrorMessage getApiErrorMessage(ConstraintViolationException exception) {
        ApiErrorMessage apiErrorMessage = ApiErrorMessage.valueOf(AppErrorCatalog.VALIDATION_ERROR);

        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            Path.Node lastNode = getLastNodePath(constraintViolation.getPropertyPath().iterator());

            apiErrorMessage.appendConstraintError(lastNode.getName(), constraintViolation.getMessage());
        }

        return apiErrorMessage;
    }

    private static Path.Node getLastNodePath(Iterator<Path.Node> iterator) {
        Path.Node lastElement = iterator.next();

        while (iterator.hasNext()) {
            lastElement = iterator.next();
        }

        return lastElement;
    }

}
