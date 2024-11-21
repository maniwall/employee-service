package com.assessment.employeeservice.repository;

import com.assessment.employeeservice.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Modifying
    @Query(value = "UPDATE Employee e set e.department = null WHERE e.id= :id")
    void updateEmployeeDepartmentId(@Param("id") Integer id);

//    @Modifying
//    @Transactional
//    @Query(value = "delete from Employee e where e.id=:id")
//    void deleteByEmployeeId(@Param("id") Integer id);

    Optional<Employee> findByFirstname(String fname);
}
