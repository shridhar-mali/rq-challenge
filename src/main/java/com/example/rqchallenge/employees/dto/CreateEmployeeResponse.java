package com.example.rqchallenge.employees.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeResponse {
    private String status;
    private Employee data;
    private String message;

    public EmployeeResponse() {
    }

    public EmployeeResponse(String status, Employee data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public EmployeeResponse(String status, Employee data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getData() {
        return data;
    }

    public void setData(Employee data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
