package com.barcelo.api.rest.demo.model;

import com.barcelo.api.rest.demo.error.ApiError;

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
    
    public static ApiErrorMessage valueOf(ApiError apiError) {
        ApiErrorMessage errorMessage = new ApiErrorMessage();

        errorMessage.setCode(apiError.getCode());
        errorMessage.setMessage(apiError.getMessage());
        errorMessage.setDeveloperMessage(apiError.getDeveloperMessage());
        errorMessage.setStatus(apiError.getHttpStatus().getStatusCode());
        
        return errorMessage;
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

}
