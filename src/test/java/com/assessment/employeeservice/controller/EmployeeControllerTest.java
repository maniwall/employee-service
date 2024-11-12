package com.assessment.employeeservice.controller;

import com.assessment.employeeservice.dto.Address;
import com.assessment.employeeservice.dto.Department;
import com.assessment.employeeservice.dto.EmployeeRequest;
import com.assessment.employeeservice.service.EmployeeService;
import com.assessment.employeeservice.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

//@WebMvcTest(EmployeeController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private EmployeeRequest employeeRequest;

    @BeforeEach
    void setup() {
        employeeRequest = new EmployeeRequest("fname1","lname1","test1@gmail.com", LocalDate.of(2020,11,15),
                "6789965777", Gender.MALE, new Address("306","BTM1","3456"),
                new Department("SE2","IT2"));
    }

    @Test
    public void testCreateEmployee() throws Exception{
        Mockito.when(employeeService.createEmployee(employeeRequest)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/employee").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstname\": \"fname1\",\n" +
                        "    \"lastname\": \"lname1\",\n" +
                        "    \"email\": \"test1@gmail.com\",\n" +
                        "    \"dob\" :\"2020-11-15\",\n" +
                        "    \"mobile\": \"6789965777\",\n" +
                        "    \"gender\": \"MALE\",\n" +
                        "    \"address\": {\n" +
                        "        \"house_number\": \"306\",\n" +
                        "        \"street\": \"BTM1\",\n" +
                        "        \"zipcode\": \"3456\"\n" +
                        "    },\n" +
                        "    \"department\": {\n" +
                        "        \"name\": \"SE2\",\n" +
                        "        \"sector\": \"IT2\"\n" +
                        "    }\n" +
                        "}")).andExpect(MockMvcResultMatchers.status().isCreated());
    }

}
