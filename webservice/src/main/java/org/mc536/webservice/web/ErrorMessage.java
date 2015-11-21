package org.mc536.webservice.web;

public class ErrorMessage {

    private String errorUrl;

    private String message;

    private Object details;

    public ErrorMessage(String errorUrl, String message) {
        this.errorUrl = errorUrl;
        this.message = message;
    }

    public ErrorMessage(String errorUrl, String message, Object details) {
        this.errorUrl = errorUrl;
        this.message = message;
        this.details = details;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public String getMessage() {
        return message;
    }

    public Object getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "errorUrl='" + errorUrl + '\'' +
                ", message='" + message + '\'' +
                ", details=" + details +
                '}';
    }
}
