package com.barcelo.api.rest.demo.model.error;


public class ApiConstraintError {

    private String item;

    private String message;

    public ApiConstraintError(String item, String message) {
        this.item = item;
        this.message = message;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
