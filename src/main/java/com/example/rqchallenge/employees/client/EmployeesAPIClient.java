package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(url = "${employee.api.url}", name = "EmployeesAPIClient")
public interface EmployeesAPIClient {
    @GetMapping(value = "/api/v1/employees")
    EmployeesResponse getAllEmployees();

    @GetMapping(value = "/api/v1/employee/{id}")
    EmployeeResponse getEmployeeById(@PathVariable(value = "id") Integer id);

    @PostMapping(value = "/api/v1/create")
    CreateEmployeeResponse createEmployee(@RequestBody CreatedEmployeeData employee);

    @DeleteMapping(value = "/api/v1/delete/{id}")
    DeleteEmployeeResponse deleteEmployeeById(@PathVariable(value = "id") String id);
}
