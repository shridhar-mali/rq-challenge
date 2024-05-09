package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.dto.CreatedEmployeeData;
import com.example.rqchallenge.employees.dto.Employee;

import java.util.List;

public interface IEmployeeServiceClient {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Integer id);

    CreatedEmployeeData createEmployee(Employee employee);

    String deleteEmployeeById(String id);
}
