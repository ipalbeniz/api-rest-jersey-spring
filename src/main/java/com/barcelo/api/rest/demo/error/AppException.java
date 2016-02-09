package com.barcelo.api.rest.demo.error;

import javax.ws.rs.core.Response;

public class AppException extends Exception {

    public enum ApiError {

        GENERIC_ERROR(Response.Status.INTERNAL_SERVER_ERROR, 1, "Unknown error", "An unexpected error has occurred"),
        INTEGRATION_ERROR(Response.Status.INTERNAL_SERVER_ERROR, 2, "Integration error", "An error has occurred executing the integration");

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

    private ApiError apiError;

    public AppException(ApiError apiError) {

        super(apiError.getMessage());
        this.apiError = apiError;
    }

    public AppException(ApiError apiError, Throwable cause) {

        super(apiError.getMessage(), cause);
        this.apiError = apiError;
    }

    public ApiError getApiError() {
        return this.apiError;
    }
}
