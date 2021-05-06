package com.rollingstone.rollingstoneapi.exceptions;

public class RestAPIExceptionInfo {
    private final String message;
    private final String details;

    public RestAPIExceptionInfo(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
