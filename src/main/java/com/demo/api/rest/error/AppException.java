package com.demo.api.rest.error;

public class AppException extends Exception {
    
    private AppErrorCatalog appErrorCatalog;

    public AppException(AppErrorCatalog appErrorCatalog) {

        super(appErrorCatalog.getMessage());
        this.appErrorCatalog = appErrorCatalog;
    }

    public AppException(AppErrorCatalog appErrorCatalog, Throwable cause) {

        super(appErrorCatalog.getMessage(), cause);
        this.appErrorCatalog = appErrorCatalog;
    }

    public AppErrorCatalog getAppErrorCatalog() {
        return this.appErrorCatalog;
    }
}
