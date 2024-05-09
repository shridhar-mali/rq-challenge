package com.example.rqchallenge.employees.exception;

public class ErrorMessage {

    private final String message;
    private final String description;

    public ErrorMessage(String message, String description) {

        this.message = message;
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
