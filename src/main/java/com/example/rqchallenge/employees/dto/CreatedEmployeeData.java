package com.example.rqchallenge.employees.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CreatedEmployeeData implements Serializable {
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("salary")
    private int salary;
    @JsonProperty("age")
    private int employeeAge;
    @JsonProperty("image")
    private String profileImage;

    public CreatedEmployeeData() {
    }

    public CreatedEmployeeData(int id, String name, int salary, int employeeAge, String profileImage) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.employeeAge = employeeAge;
        this.profileImage = profileImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(int employeeAge) {
        this.employeeAge = employeeAge;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
