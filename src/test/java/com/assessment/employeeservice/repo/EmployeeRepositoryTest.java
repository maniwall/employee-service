package com.assessment.employeeservice.repo;

import com.assessment.employeeservice.entity.Address;
import com.assessment.employeeservice.entity.Department;
import com.assessment.employeeservice.entity.Employee;
import com.assessment.employeeservice.repository.EmployeeRepository;
import com.assessment.employeeservice.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager entityManager;

    Integer persistEmployeeId;

    @BeforeEach
    void setup() {

        Employee emp = new Employee("fname", "lname", "testing@gmail.com"
                , LocalDate.of(2020, 10, 10)
                , "1122334455", Gender.MALE
                , new Address("street","city","1234")
                , new Department("cs","it"));

        Employee persistEmployee = entityManager.persist(emp);
        persistEmployeeId = persistEmployee.getId();
    }

    @Test
    public void testFindById() {
        Employee employee = employeeRepository.findById(persistEmployeeId).get();
        assertEquals("fname", employee.getFirstname());
    }
}
