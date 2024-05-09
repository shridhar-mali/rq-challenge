package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.client.EmployeeServiceClient;
import com.example.rqchallenge.employees.dto.CreatedEmployeeData;
import com.example.rqchallenge.employees.dto.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeServiceClient employeeServiceClient;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void shouldGetAllEmployees() {
        when(employeeServiceClient.getAllEmployees())
                .thenReturn(List.of(new Employee(1, "alex", 100, 20, "image")));
        List<Employee> allEmployees = employeeService.getAllEmployees();

        assertThat(allEmployees).hasSize(1);
    }

    @Test
    void shouldGetEmployeeById() {
        when(employeeServiceClient.getEmployeeById(1))
                .thenReturn(new Employee(1, "alex", 100, 20, "image"));
        Employee employee = employeeService.getEmployeeById(1);

        assertThat(employee.getId()).isEqualTo(1);
        assertThat(employee.getEmployeeName()).isEqualTo("alex");
    }

    @Test
    void shouldCreateEmployee() {
        Employee employeeData = new Employee(1, "alex", 100, 20, "image");
        when(employeeServiceClient.createEmployee(any(Employee.class)))
                .thenReturn(new CreatedEmployeeData(1, "alex", 100, 20, "image"));
        CreatedEmployeeData employee = employeeService.createEmployee(employeeData);

        assertThat(employee.getId()).isEqualTo(1);
        assertThat(employee.getEmployeeName()).isEqualTo("alex");
    }

    @Test
    void shouldDeleteEmployee() {
        when(employeeServiceClient.deleteEmployeeById("1"))
                .thenReturn("1");
        String id = employeeService.deleteEmployeeById("1");

        assertThat(id).isEqualTo("1");
    }

    @Test
    void shouldReturnHighestSalaryForEmployees() {
        when(employeeServiceClient.getAllEmployees())
                .thenReturn(List.of(new Employee(1, "alex", 100, 20, "image"),
                        new Employee(2, "ram", 1000, 20, "image"),
                        new Employee(3, "joe", 101, 20, "image"),
                        new Employee(15, "brain", 9000, 20, "image"),
                        new Employee(10, "head", 2000, 20, "image")));
        Integer highestSalaryOfEmployees = employeeService.getHighestSalaryOfEmployees();

        assertThat(highestSalaryOfEmployees).isEqualTo(9000);
    }

    @Test
    void shouldReturnTop10HighestSalaryEmployees() {
        when(employeeServiceClient.getAllEmployees())
                .thenReturn(List.of(new Employee(1, "alex", 102, 20, "image"),
                        new Employee(2, "ram", 1000, 20, "image"),
                        new Employee(3, "joe", 101, 20, "image"),
                        new Employee(15, "brain", 9000, 20, "image"),
                        new Employee(10, "head", 2000, 20, "image")));
        List<String> top10HighestEarningEmployeeNames = employeeService.getTop10HighestEarningEmployeeNames();

        assertThat(top10HighestEarningEmployeeNames).hasSize(5);
        assertThat(top10HighestEarningEmployeeNames).first().isEqualTo("brain");
        assertThat(top10HighestEarningEmployeeNames).last().isEqualTo("joe");
    }

    @Test
    void shouldReturnEmployeesByNameSearch() {
        when(employeeServiceClient.getAllEmployees())
                .thenReturn(List.of(new Employee(1, "Radheram", 102, 20, "image"),
                        new Employee(2, "ram", 1000, 20, "image"),
                        new Employee(3, "Gangaram", 101, 20, "image"),
                        new Employee(15, "Bolaraman", 9000, 20, "image"),
                        new Employee(10, "Rampyaree", 2000, 20, "image")));
        List<Employee> employeesByNameSearch = employeeService.getEmployeesByNameSearch("ram");

        assertThat(employeesByNameSearch).hasSize(4);
        assertThat(employeesByNameSearch).first().extracting(Employee::getEmployeeName).isEqualTo("ram");
        assertThat(employeesByNameSearch).last().extracting(Employee::getEmployeeName).isEqualTo("Radheram");
    }


}