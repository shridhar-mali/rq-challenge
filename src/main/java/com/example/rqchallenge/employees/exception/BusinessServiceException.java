package com.example.rqchallenge.employees.exception;

public class BusinessServiceException extends RuntimeException {
    public BusinessServiceException() {
    }

    public BusinessServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessServiceException(String message) {
        super(message);
    }
}
