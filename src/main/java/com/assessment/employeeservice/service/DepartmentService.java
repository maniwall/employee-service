package com.assessment.employeeservice.service;

import com.assessment.employeeservice.dto.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getDepartments();

    boolean createDepartment(Department department);

    boolean deleteDepartment(Integer departmentId) throws Exception;

    boolean updateDepartment(Department department) throws Exception;
}
