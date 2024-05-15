package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.client.EmployeesAPIClient;
import com.example.rqchallenge.employees.client.IEmployeeServiceClient;
import com.example.rqchallenge.employees.dto.CreatedEmployeeData;
import com.example.rqchallenge.employees.dto.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeesAPIClient employeesAPIClient;

    public EmployeeService(EmployeesAPIClient employeesAPIClient) {
        this.employeesAPIClient = employeesAPIClient;
    }

    public List<Employee> getAllEmployees() {
        return employeesAPIClient.getAllEmployees().getData();
    }


    public Employee getEmployeeById(Integer id) {
        LOGGER.info("getting employee details for id {}", id);
        return employeesAPIClient.getEmployeeById(id).getData();
    }


    public CreatedEmployeeData createEmployee(CreatedEmployeeData employee) {
        return employeesAPIClient.createEmployee(employee).getData();
    }


    public String deleteEmployeeById(String id) {
        LOGGER.info("deleting employee with id {}", id);
        return employeesAPIClient.deleteEmployeeById(id).getData();
    }

    public Integer getHighestSalaryOfEmployees() {
        return getAllEmployees()
                .stream()
                .mapToInt(Employee::getEmployeeSalary)
                .max()
                .getAsInt();
    }

    public List<String> getTop10HighestEarningEmployeeNames() {
        return getAllEmployees()
                .stream()
                .sorted(comparingInt(Employee::getEmployeeSalary).reversed())
                .map(Employee::getEmployeeName)
                .limit(10)
                .collect(toList());
    }

    public List<Employee> getEmployeesByNameSearch(String searchString) {
        LOGGER.info("searching employees with search string {}", searchString);
        return getAllEmployees()
                .stream()
                .filter(employee -> employee.getEmployeeName().contains(searchString))
                .sorted(comparing((Employee employee) -> !employee.getEmployeeName().equals(searchString)) // Exact match first
                        .thenComparing(Employee::getEmployeeName))
                .collect(toList());
    }
}
