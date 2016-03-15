package com.demo.api.rest.error;

import javax.ws.rs.core.Response;

public enum AppErrorCatalog {

    GENERIC_ERROR(Response.Status.INTERNAL_SERVER_ERROR, 1, "Unknown error", "An unexpected error has occurred. Please contact the technical support team."),
    NOT_FOUND_ERROR(Response.Status.NOT_FOUND, 2, "Not Found", "The resource was not found"),
    VALIDATION_ERROR(Response.Status.BAD_REQUEST, 3, "Validation error", "The request is not valid"),
    PARSE_ERROR(Response.Status.BAD_REQUEST, 4, "Parse error", "The request is not parseable. Invalid JSON format"),

    RESOURCE_WITH_ID_NOT_FOUND_ERROR(Response.Status.NOT_FOUND, 4, "Resource with ID Not Found", "The resource with that ID was not found"),
    ID_REQUIRED_ERROR(Response.Status.BAD_REQUEST, 5, "Validation error", "The ID is required");

    private Response.Status httpStatus;
    private int code;
    private String message;
    private String developerMessage;

    AppErrorCatalog(Response.Status httpStatus, int code, String message, String developerMessage) {
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