package com.example.rqchallenge.employees.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateEmployeeResponse {
    private String status;
    private CreatedEmployeeData data;

    public CreateEmployeeResponse() {
    }

    public CreateEmployeeResponse(String status, CreatedEmployeeData data) {
        this.status = status;
        this.data = data;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CreatedEmployeeData getData() {
        return data;
    }

    public void setData(CreatedEmployeeData data) {
        this.data = data;
    }
}
