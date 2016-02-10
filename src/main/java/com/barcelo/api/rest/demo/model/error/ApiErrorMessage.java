package com.barcelo.api.rest.demo.model.error;

import com.barcelo.api.rest.demo.error.ApiErrorCatalog;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ApiErrorMessage {

    /**
     * contains the same HTTP Status code returned by the server
     */
    private int status;

    /**
     * application specific error code
     */
    private int code;

    /**
     * message describing the error
     */
    private String message;

    /**
     * extra information that might useful for developers
     */
    private String developerMessage;

    /**
     * more info about the error
     */
    private List<ApiConstraintError> constraintErrors;
    
    public static ApiErrorMessage valueOf(ApiErrorCatalog apiErrorCatalog) {
        ApiErrorMessage errorMessage = new ApiErrorMessage();

        errorMessage.setCode(apiErrorCatalog.getCode());
        errorMessage.setMessage(apiErrorCatalog.getMessage());
        errorMessage.setDeveloperMessage(apiErrorCatalog.getDeveloperMessage());
        errorMessage.setStatus(apiErrorCatalog.getHttpStatus().getStatusCode());
        
        return errorMessage;
    }

    public void appendConstraintError(String item, String message) {
        if (constraintErrors == null) {
            constraintErrors = new ArrayList<>();
        }
        constraintErrors.add(new ApiConstraintError(item, message));
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public List<ApiConstraintError> getConstraintErrors() {
        return constraintErrors;
    }

    public void setConstraintErrors(List<ApiConstraintError> constraintErrors) {
        this.constraintErrors = constraintErrors;
    }
}
