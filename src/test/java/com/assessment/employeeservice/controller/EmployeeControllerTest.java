package com.assessment.employeeservice.controller;

import com.assessment.employeeservice.dto.AddressDTO;
import com.assessment.employeeservice.dto.DepartmentDTO;
import com.assessment.employeeservice.dto.EmployeeDTO;
import com.assessment.employeeservice.service.EmployeeService;
import com.assessment.employeeservice.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private EmployeeDTO employeeDTO;

    private static final String invalidEmailEmployeeRequest = """
            {
                "firstname": "fname1",
                "lastname": "lname1",
                "email": "test_email",
                "dob" :"2020-11-15",
                "mobile": "6789965777",
                "gender": "MALE1",
                "address": {
                    "house_number": "306",
                    "street": "BTM1",
                    "zipcode": "3456"
                },
                "department": {
                    "name": "SE2",
                    "sector": "IT2"
                }
            }
            """;

    @BeforeEach
    void setup() {
        employeeDTO = new EmployeeDTO("fname1", "lname1", "test1@gmail.com", LocalDate.of(2020, 11, 15),
                "6789965777", Gender.MALE, new AddressDTO("306", "BTM1", "3456"),
                new DepartmentDTO("SE2", "IT2"));
    }

    @Test
    public void testInvalidEmailRequest() throws Exception {
        mockMvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidEmailEmployeeRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Create Employee")
    public void testCreateEmployee() throws Exception {
        Mockito.when(employeeService.createEmployee(employeeDTO)).thenReturn(true);
        mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON)
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

    @Test
    @DisplayName("Update Employee")
    public void testUpdateEmployee_negative() throws Exception {
        Mockito.when(employeeService.updateEmployee(employeeDTO)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.put("/employee").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstname\": \"\",\n" +
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
                                "}")).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("{\"firstname\":\"FirstName must be between 3 and 32 characters\"}"));
    }

    @Test
    @DisplayName("Delete Employee")
    public void testDeleteEmployee() throws Exception {
        Mockito.when(employeeService.deleteEmployee(1)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Employee Deleted Successfully!!"));
    }

    @Test
    @DisplayName("Get Employees")
    public void testGetEmployees() throws Exception {
        Mockito.when(employeeService.getEmployees()).thenReturn(Arrays.asList(employeeDTO));
        mockMvc.perform(MockMvcRequestBuilders.get("/employee"))
                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.content().json();
    }


}
