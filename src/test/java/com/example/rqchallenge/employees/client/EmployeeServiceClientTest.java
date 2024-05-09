package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.dto.*;
import com.example.rqchallenge.employees.exception.BusinessServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.DELETE;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class EmployeeServiceClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeServiceClient employeeServiceClient;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(employeeServiceClient, "employeeAPIBaseUrl", "https://dummy.restapiexample.com");
//        ReflectionTestUtils.setField(employeeServiceClient, EmployeeServiceClient.class, "employeeAPIBaseUrl", "https://dummy.restapiexample.com", String.class);
    }

    @Test
    void shouldGetAllEmployees() {
        when(restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employees", EmployeesResponse.class))
                .thenReturn(ResponseEntity.ok().body(new EmployeesResponse("success",
                                                                            List.of(new Employee(1, "alex", 100, 20, "image")),
                                                                    "Successfully! All records has been fetched.")));
        List<Employee> allEmployees = employeeServiceClient.getAllEmployees();

        assertThat(allEmployees).hasSize(1);
    }

    @Test
    void shouldThrowExceptionWhenGetAllEmployeesApiCallFails() {
        when(restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employees", EmployeesResponse.class))
                .thenReturn(ResponseEntity.internalServerError().build());
        assertThatCode(() -> employeeServiceClient.getAllEmployees()).isExactlyInstanceOf(BusinessServiceException.class);
    }

    @Test
    void shouldGetEmployeeById() {
        when(restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employee/1", EmployeeResponse.class))
                .thenReturn(ResponseEntity.ok().body(new EmployeeResponse("success",
                        new Employee(1, "alex", 100, 20, "image"),
                        "Successfully! Record has been fetched.")));
        Employee employee = employeeServiceClient.getEmployeeById(1);

        assertThat(employee.getId()).isEqualTo(1);
        assertThat(employee.getEmployeeName()).isEqualTo("alex");
    }

    @Test
    void shouldThrowExceptionWhenGetEmployeeByIdApiCallFails() {
        when(restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employee/1", EmployeeResponse.class))
                .thenReturn(ResponseEntity.internalServerError().build());
        assertThatCode(() -> employeeServiceClient.getEmployeeById(1)).isExactlyInstanceOf(BusinessServiceException.class);
    }

    @Test
    void shouldCreateEmployee() {
        CreatedEmployeeData employeeData = new CreatedEmployeeData(1, "alex", 100, 20, "image");
        when(restTemplate.postForEntity(anyString(), any(CreatedEmployeeData.class), any()))
                .thenReturn(ResponseEntity.ok().body(new CreateEmployeeResponse("success",
                        new CreatedEmployeeData(1, "alex", 100, 20, "image"))));
        CreatedEmployeeData employee = employeeServiceClient.createEmployee(employeeData);

        verify(restTemplate).postForEntity("https://dummy.restapiexample.com/api/v1/create", employeeData, CreateEmployeeResponse.class);

        assertThat(employee.getId()).isEqualTo(1);
        assertThat(employee.getEmployeeName()).isEqualTo("alex");
    }

    @Test
    void shouldThrowExceptionWhenCreateEmployeeApiCallFails() {
        CreatedEmployeeData employeeData = new CreatedEmployeeData(1, "alex", 100, 20, "image");
        when(restTemplate.postForEntity(anyString(), any(CreatedEmployeeData.class), any()))
                .thenReturn(ResponseEntity.internalServerError().build());
        assertThatCode(() -> employeeServiceClient.createEmployee(employeeData)).isExactlyInstanceOf(BusinessServiceException.class);
    }

    @Test
    void shouldDeleteEmployee() {
        when(restTemplate.exchange("https://dummy.restapiexample.com/api/v1/delete/{id}", DELETE, null, new ParameterizedTypeReference<DeleteEmployeeResponse>() {}, "1"))
                .thenReturn(ResponseEntity.ok().body(new DeleteEmployeeResponse("success", "1", "Deleted record")));
        String id = employeeServiceClient.deleteEmployeeById("1");

        assertThat(id).isEqualTo("1");
    }

    @Test
    void shouldThrowExceptionWhenDeleteEmployeeApiCallFails() {
        Employee employeeData = new Employee(1, "alex", 100, 20, "image");
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), any(ParameterizedTypeReference.class), anyString()))
                .thenReturn(ResponseEntity.internalServerError().build());
        assertThatCode(() -> employeeServiceClient.deleteEmployeeById("1")).isExactlyInstanceOf(BusinessServiceException.class);
    }
}