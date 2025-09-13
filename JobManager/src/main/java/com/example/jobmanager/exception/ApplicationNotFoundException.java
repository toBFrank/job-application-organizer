package com.example.jobmanager.exception;

public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException(final String message) {
        super(message);
    }
}
