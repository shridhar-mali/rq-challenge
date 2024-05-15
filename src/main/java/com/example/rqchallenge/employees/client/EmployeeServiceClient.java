package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.dto.*;
import com.example.rqchallenge.employees.exception.BusinessServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.DELETE;

@Deprecated(since = "FeignClient")
@Component
public class EmployeeServiceClient implements IEmployeeServiceClient {

    private static final String ALL_EMPLOYEES_ENDPOINT_URI = "api/v1/employees";
    private static final String EMPLOYEE_DETAILS_ENDPOINT_URI = "api/v1/employee/%s";
    private static final String CREATE_EMPLOYEE_ENDPOINT_URI = "api/v1/create";
    private static final String DELETE_EMPLOYEE_ENDPOINT_URI = "api/v1/delete/{id}";
    private final RestTemplate restTemplate;
    private final String employeeAPIBaseUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceClient.class);

    public EmployeeServiceClient(RestTemplate restTemplate,  @Value("${employee.api.url}") String employeeAPIBaseUrl) {
        this.restTemplate = restTemplate;
        this.employeeAPIBaseUrl = employeeAPIBaseUrl;
    }

    private <T> T performGetRequest(String endpoint, Class<T> responseType) {
        LOGGER.info("Making GET API call for endpoint {}", endpoint);
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(endpoint, responseType);
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            return responseEntity.getBody();
        }

        throw new BusinessServiceException(String.format("API call for endpoint: '%s' failed with response status: '%s'", endpoint, responseEntity.getStatusCode()));
    }

    private <R, T> T performPostRequest(String endpoint, R request, Class<T> responseType) {
        LOGGER.info("Making POST  API call for endpoint {}", endpoint);
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(endpoint, request, responseType);
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            return responseEntity.getBody();
        }

        throw new BusinessServiceException(String.format("API call for endpoint: '%s' failed with response status: '%s'", endpoint, responseEntity.getStatusCode()));
    }

    private DeleteEmployeeResponse performDeleteRequest(String endpoint, String id) {
        LOGGER.info("Making DELETE API call for endpoint {}", endpoint);
        ResponseEntity<DeleteEmployeeResponse> responseEntity = restTemplate.exchange(endpoint, DELETE, null, new ParameterizedTypeReference<>() {}, id);
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            return responseEntity.getBody();
        }

        throw new BusinessServiceException(String.format("API call for endpoint: '%s' failed with response status: '%s'", endpoint, responseEntity.getStatusCode()));
    }

    @Override
    public List<Employee> getAllEmployees() {
        EmployeesResponse employeesResponse = performGetRequest(String.join("/", employeeAPIBaseUrl, ALL_EMPLOYEES_ENDPOINT_URI), EmployeesResponse.class);
        return employeesResponse.getData();
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        String employeeDetailsEndpoint = String.format(String.join("/", employeeAPIBaseUrl, EMPLOYEE_DETAILS_ENDPOINT_URI), id);
        EmployeeResponse employeeResponse = performGetRequest(employeeDetailsEndpoint, EmployeeResponse.class);
        return employeeResponse.getData();
    }

    @Override
    public CreatedEmployeeData createEmployee(CreatedEmployeeData employee) {
        CreateEmployeeResponse employeeResponse = performPostRequest(String.join("/", employeeAPIBaseUrl, CREATE_EMPLOYEE_ENDPOINT_URI), employee, CreateEmployeeResponse.class);
        return employeeResponse.getData();
    }

    @Override
    public String deleteEmployeeById(String id) {
        DeleteEmployeeResponse deleteEmployeeResponse = performDeleteRequest(String.join("/", employeeAPIBaseUrl, DELETE_EMPLOYEE_ENDPOINT_URI), id);
        return deleteEmployeeResponse.getData();
    }


}
