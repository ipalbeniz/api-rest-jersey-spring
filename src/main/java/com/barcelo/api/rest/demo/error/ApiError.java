package com.barcelo.api.rest.demo.error;

import javax.ws.rs.core.Response;

public enum ApiError {

    GENERIC_ERROR(Response.Status.INTERNAL_SERVER_ERROR, 1, "Unknown error", "An unexpected error has occurred"),
    NOT_FOUND_ERROR(Response.Status.NOT_FOUND, 2, "Not Found", "The resource was not found"),
    INTEGRATION_ERROR(Response.Status.INTERNAL_SERVER_ERROR, 3, "Integration error", "An error has occurred executing the integration");

    private Response.Status httpStatus;
    private int code;
    private String message;
    private String developerMessage;

    ApiError(Response.Status httpStatus, int code, String message, String developerMessage) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.developerMessage = developerMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Response.Status getHttpStatus() {
        return httpStatus;
    }

}