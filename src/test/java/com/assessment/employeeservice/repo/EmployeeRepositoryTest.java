package com.assessment.employeeservice.repo;

import com.assessment.employeeservice.entity.Address;
import com.assessment.employeeservice.entity.Department;
import com.assessment.employeeservice.entity.Employee;
import com.assessment.employeeservice.repository.DepartmentRepository;
import com.assessment.employeeservice.repository.EmployeeRepository;
import com.assessment.employeeservice.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee emp;

    @BeforeEach
    void setup() {

        emp = new Employee("fname", "lname", "testing@gmail.com"
                , LocalDate.of(2020, 10, 10)
                , "1122334455", Gender.MALE);
//                , new Address("street", "city", "1234")
//                , new Department("ECE", "it"));
    }

    @Test
    public void testFindEmployeeById() {
        employeeRepository.save(emp);
        Optional<Employee> employee = employeeRepository.findByFirstname("fname");
        assertThat(employee).isPresent();
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = employeeRepository.save(emp);
        assertThat(employee).isNotNull();
        assertThat(employee.getFirstname()).isEqualTo("fname");
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = employeeRepository.save(emp);
        employee.setFirstname("TestFirstName");

        Employee updatedEmployee = employeeRepository.save(employee);
        assertThat(updatedEmployee.getFirstname()).isEqualTo("TestFirstName");
    }
}
