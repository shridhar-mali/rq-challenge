package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.dto.CreatedEmployeeData;
import com.example.rqchallenge.employees.dto.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.Integer.parseInt;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class EmployeeController implements IEmployeeController{

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ok(employeeService.getAllEmployees());
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        return ok(employeeService.getEmployeesByNameSearch(searchString));
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        return ok(employeeService.getEmployeeById(parseInt(id)));
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return ok(employeeService.getHighestSalaryOfEmployees());
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return ok(employeeService.getTop10HighestEarningEmployeeNames());
    }

    @Override
    public ResponseEntity<CreatedEmployeeData> createEmployee(Employee employee) {
        return ok(employeeService.createEmployee(employee));
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        return ok(employeeService.deleteEmployeeById(id));
    }
}
