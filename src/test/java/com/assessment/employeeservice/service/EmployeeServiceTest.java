package com.assessment.employeeservice.service;

import com.assessment.employeeservice.dto.EmployeeRequest;
import com.assessment.employeeservice.entity.Address;
import com.assessment.employeeservice.entity.Department;
import com.assessment.employeeservice.entity.Employee;
import com.assessment.employeeservice.repository.EmployeeRepository;
import com.assessment.employeeservice.utils.Gender;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setup() {
        Department department = new Department();
        Address address = new Address();
        Optional<Employee> employee = Optional.of(new Employee(1, "fname", "lname", "testing@gmail.com", LocalDate.now(), "1122334455", Gender.MALE, address, department));
        Mockito.when(employeeRepository.findById(1)).thenReturn(employee);
    }

    @SneakyThrows
    @Test
    public void testGetEmployeeById() {
        String employeeFirstName = "fname";
        EmployeeRequest employee = employeeService.getEmployee(1);
        assertEquals(employeeFirstName, employee.getFirstname());
    }
}
