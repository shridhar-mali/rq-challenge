package com.example.rqchallenge.employees.service;

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

    private IEmployeeServiceClient iEmployeeServiceClient;

    public EmployeeService(IEmployeeServiceClient iEmployeeServiceClient) {
        this.iEmployeeServiceClient = iEmployeeServiceClient;
    }

    public List<Employee> getAllEmployees() {
        return iEmployeeServiceClient.getAllEmployees();
    }


    public Employee getEmployeeById(Integer id) {
        LOGGER.info("getting employee details for id {}", id);
        return iEmployeeServiceClient.getEmployeeById(id);
    }


    public CreatedEmployeeData createEmployee(Employee employee) {
        return iEmployeeServiceClient.createEmployee(employee);
    }


    public String deleteEmployeeById(String id) {
        LOGGER.info("deleting employee with id {}", id);
        return iEmployeeServiceClient.deleteEmployeeById(id);
    }

    public Integer getHighestSalaryOfEmployees() {
        return iEmployeeServiceClient.getAllEmployees()
                .stream()
                .mapToInt(Employee::getEmployeeSalary)
                .max()
                .getAsInt();
    }

    public List<String> getTop10HighestEarningEmployeeNames() {
        return iEmployeeServiceClient.getAllEmployees()
                .stream()
                .sorted(comparingInt(Employee::getEmployeeSalary).reversed())
                .map(Employee::getEmployeeName)
                .limit(10)
                .collect(toList());
    }

    public List<Employee> getEmployeesByNameSearch(String searchString) {
        LOGGER.info("searching employees with search string {}", searchString);
        return iEmployeeServiceClient.getAllEmployees()
                .stream()
                .filter(employee -> employee.getEmployeeName().contains(searchString))
                .sorted(comparing((Employee employee) -> !employee.getEmployeeName().equals(searchString)) // Exact match first
                        .thenComparing(Employee::getEmployeeName))
                .collect(toList());
    }
}
