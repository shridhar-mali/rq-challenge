package com.example.rqchallenge.employees.controller;


import com.example.rqchallenge.employees.dto.CreatedEmployeeData;
import com.example.rqchallenge.employees.dto.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestClientException;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void shouldReturnAllEmployees()
            throws Exception {

        Employee alex = new Employee(1, "alex", 100, 20, "image");

        List<Employee> allEmployees = List.of(alex);

        given(employeeService.getAllEmployees()).willReturn(allEmployees);

        mvc.perform(MockMvcRequestBuilders.get("/employees/")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].employee_name", is(alex.getEmployeeName())));
    }

    @Test
    public void shouldThrowExceptionForAllEmployeesWhenRestApiFails()
            throws Exception {

        given(employeeService.getAllEmployees()).willThrow(new RestClientException("Service unavailable"));

        mvc.perform(MockMvcRequestBuilders.get("/employees/")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Service unavailable")))
                .andExpect(jsonPath("$.description", is("uri=/employees/")));
    }

    @Test
    public void shouldReturnEmployeeDetailsForGivenId()
            throws Exception {

        Employee alex = new Employee(1, "alex", 100, 20, "image");

        given(employeeService.getEmployeeById(1)).willReturn(alex);

        mvc.perform(MockMvcRequestBuilders.get("/employees/1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employee_name", is(alex.getEmployeeName())))
                .andExpect(jsonPath("$.id", is(alex.getId())));
    }

    @Test
    public void shouldCreateEmployeeWithGivenDetails()
            throws Exception {

        Employee alex = new Employee(1, "alex", 100, 20, "image");
        String employeeJsonString = new ObjectMapper().writeValueAsString(alex);

        given(employeeService.createEmployee(any(Employee.class))).willReturn(new CreatedEmployeeData(1, "alex", 100, 20, "image"));

        mvc.perform(MockMvcRequestBuilders.post("/employees/create")
                        .content(employeeJsonString)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(alex.getEmployeeName())))
                .andExpect(jsonPath("$.id", is(alex.getId())));
    }

    @Test
    public void shouldDeleteEmployeeForGivenId()
            throws Exception {

        given(employeeService.deleteEmployeeById("1")).willReturn("1");

        mvc.perform(MockMvcRequestBuilders.delete("/employees/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void shouldThrowExceptionDeleteEmployeeWhenRestApiFails()
            throws Exception {

        given(employeeService.deleteEmployeeById(anyString())).willThrow(new RestClientException("Service unavailable"));

        mvc.perform(MockMvcRequestBuilders.delete("/employees/delete/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Service unavailable")))
                .andExpect(jsonPath("$.description", is("uri=/employees/delete/1")));
    }

    @Test
    public void shouldReturnEmployeesByNameSearch()
            throws Exception {

        given(employeeService.getEmployeesByNameSearch("ram")).willReturn(List.of(new Employee(1, "Radheram", 102, 20, "image"),
                new Employee(2, "ram", 1000, 20, "image"),
                new Employee(3, "Gangaram", 101, 20, "image"),
                new Employee(15, "Bolaraman", 9000, 20, "image"),
                new Employee(10, "Rampyaree", 2000, 20, "image")));

        mvc.perform(MockMvcRequestBuilders.get("/employees/search/ram/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].employee_name", is("Radheram")))
                .andExpect(jsonPath("$[4].employee_name", is("Rampyaree")));
    }

    @Test
    public void shouldReturnHighestSalaryOfEmployees()
            throws Exception {

        given(employeeService.getHighestSalaryOfEmployees()).willReturn(1000);

        mvc.perform(MockMvcRequestBuilders.get("/employees/highestSalary"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000"));
    }

    @Test
    public void shouldReturnTop10HighestEarningEmployeeNames()
            throws Exception {

        given(employeeService.getTop10HighestEarningEmployeeNames()).willReturn(List.of("ram", "radhesham", "gangaram"));

        mvc.perform(MockMvcRequestBuilders.get("/employees/topTenHighestEarningEmployeeNames/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0]", is("ram")))
                .andExpect(jsonPath("$[2]", is("gangaram")));
    }


}