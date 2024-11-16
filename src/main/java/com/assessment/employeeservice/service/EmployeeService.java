package com.assessment.employeeservice.service;

import com.assessment.employeeservice.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getEmployees();

    boolean createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployee(Integer empId) throws Exception;

    boolean deleteEmployee(Integer employeeId) throws Exception;

    boolean updateEmployee(EmployeeDTO employeeDTO) throws Exception;
}
