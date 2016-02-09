package com.barcelo.api.rest.demo.error;

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
