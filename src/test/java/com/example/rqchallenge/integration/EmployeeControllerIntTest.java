package com.example.rqchallenge.integration;

import com.example.rqchallenge.employees.dto.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@WireMockTest(httpPort = 8081)
public class EmployeeControllerIntTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldReturnAllEmployees()
            throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/employees/")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(24)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(1)));
    }

    @Test
    public void shouldReturnEmployeeDetailsForGivenId()
            throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/employees/1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employee_name", is("Tiger Nixon")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void shouldReturnEmployeesByNameSearch()
            throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/employees/search/Wil")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is(24)));
    }

    @Test
    public void shouldReturnEmployeesHighestSalary()
            throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/employees/highestSalary")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("725000"));
    }

    @Test
    public void shouldReturnTop10HighestSalaryEmployees()
            throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/employees/topTenHighestEarningEmployeeNames")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[0]", is("Paul Byrd")))
                .andExpect(jsonPath("$[9]", is("Tiger Nixon")));
    }

    @Test
    public void shouldCreateEmployee()
            throws Exception {

        Employee alex = new Employee(25000, "Brian Lara", 100, 20, "image");
        String employeeJsonString = new ObjectMapper().writeValueAsString(alex);

        mvc.perform(MockMvcRequestBuilders.post("/employees/create")
                        .contentType(APPLICATION_JSON)
                        .content(employeeJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Brian Lara")))
                .andExpect(jsonPath("$.id", is(25000)));
    }

    @Test
    public void shouldDeleteEmployee()
            throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete("/employees/delete/1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
