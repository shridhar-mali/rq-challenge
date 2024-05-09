package com.example.rqchallenge.employees.dto;

public class DeleteEmployeeResponse {
    private String status;
    private String data;
    private String message;

    public DeleteEmployeeResponse(String status, String data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
