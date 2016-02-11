package com.demo.api.rest.error;

public class AppException extends Exception {
    
    private ApiErrorCatalog apiErrorCatalog;

    public AppException(ApiErrorCatalog apiErrorCatalog) {

        super(apiErrorCatalog.getMessage());
        this.apiErrorCatalog = apiErrorCatalog;
    }

    public AppException(ApiErrorCatalog apiErrorCatalog, Throwable cause) {

        super(apiErrorCatalog.getMessage(), cause);
        this.apiErrorCatalog = apiErrorCatalog;
    }

    public ApiErrorCatalog getApiErrorCatalog() {
        return this.apiErrorCatalog;
    }
}
