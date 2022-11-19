package com.test.jsf.exception;

public class PageLoadingException extends Exception{
    private final String reason;

    public PageLoadingException(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
