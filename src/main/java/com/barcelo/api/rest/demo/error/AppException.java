package com.barcelo.api.rest.demo.error;

public class AppException extends Exception {
    
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
