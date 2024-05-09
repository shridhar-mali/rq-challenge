package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.dto.CreatedEmployeeData;
import com.example.rqchallenge.employees.dto.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public interface IEmployeeController {

    @GetMapping("/")
    ResponseEntity<List<Employee>> getAllEmployees() throws IOException;

    @GetMapping("/search/{searchString}")
    ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString);

    @GetMapping("/{id}")
    ResponseEntity<Employee> getEmployeeById(@PathVariable String id);

    @GetMapping("/highestSalary")
    ResponseEntity<Integer> getHighestSalaryOfEmployees();

    @GetMapping("/topTenHighestEarningEmployeeNames")
    ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames();

    @PostMapping("/create")
    @ResponseBody
    ResponseEntity<CreatedEmployeeData> createEmployee(@RequestBody CreatedEmployeeData employeeInput);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteEmployeeById(@PathVariable String id);

}
