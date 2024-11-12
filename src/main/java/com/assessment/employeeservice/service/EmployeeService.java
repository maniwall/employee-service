package com.assessment.employeeservice.service;

import com.assessment.employeeservice.dto.EmployeeRequest;

import java.util.List;

public interface EmployeeService {

    List<EmployeeRequest> getEmployees();

    boolean createEmployee(EmployeeRequest employeeRequest);

    EmployeeRequest getEmployee(Integer empId) throws Exception;

    boolean deleteEmployee(Integer employeeId) throws Exception;

    boolean updateEmployee(EmployeeRequest employeeRequest) throws Exception;
}
