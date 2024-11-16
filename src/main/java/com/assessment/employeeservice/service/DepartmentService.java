package com.assessment.employeeservice.service;

import com.assessment.employeeservice.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDTO> getDepartments();

    boolean createDepartment(DepartmentDTO departmentDTO) throws Exception;

    boolean deleteDepartment(Integer departmentId) throws Exception;

    boolean updateDepartment(DepartmentDTO departmentDTO) throws Exception;
}
