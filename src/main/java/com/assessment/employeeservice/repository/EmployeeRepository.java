package com.assessment.employeeservice.repository;

import com.assessment.employeeservice.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE employee e set e.department_id = null WHERE e.id= :id", nativeQuery = true)
    void updateEmployeeDepartmentId(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value = "delete from Employee e where e.id=:id")
    void deleteByEmployeeId(@Param("id") Integer id);
}
