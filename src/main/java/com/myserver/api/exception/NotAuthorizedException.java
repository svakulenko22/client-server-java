package com.myserver.api.exception;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException() {
    }

    public NotAuthorizedException(String msg) {
        super(msg);
    }
}
