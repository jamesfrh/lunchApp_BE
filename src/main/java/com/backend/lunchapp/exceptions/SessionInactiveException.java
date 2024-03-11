package com.backend.lunchapp.exceptions;

public class SessionInactiveException extends RuntimeException {

    public SessionInactiveException(String message) {
        super(message);
    }
}
