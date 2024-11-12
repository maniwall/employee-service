package com.assessment.employeeservice.service;

import com.assessment.employeeservice.dto.Address;
import com.assessment.employeeservice.dto.Department;
import com.assessment.employeeservice.dto.EmployeeRequest;
import com.assessment.employeeservice.entity.Employee;
import com.assessment.employeeservice.repository.DepartmentRepository;
import com.assessment.employeeservice.repository.EmployeeRepository;
import com.assessment.employeeservice.utils.EmployeeServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public static int calculateAge(LocalDate date) {
        LocalDate current = LocalDate.now();
        return current.getYear() - date.getYear();
    }

    @Override
    public List<EmployeeRequest> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(EmployeeServiceImpl::convertEmployeeEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean createEmployee(EmployeeRequest employeeRequest) {
        Employee employeeEntity = new Employee();
        Employee emp = convertEmployeeDtoToEntity(employeeEntity, employeeRequest);
        emp = employeeRepository.save(emp);
        return emp.getId() > 0;
    }

    @Override
    public EmployeeRequest getEmployee(Integer empId) throws Exception {
        Optional<Employee> employeeOpt = employeeRepository.findById(empId);
        return employeeOpt.map(EmployeeServiceImpl::convertEmployeeEntityToDto).orElseThrow(() -> new EmployeeServiceException("No record or entity found to fetch"));
    }

    @Override
    public boolean deleteEmployee(Integer employeeId) throws Exception {
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        if(employee.isPresent()){
            Integer employeeIdValue = employee.get().getId();
            employeeRepository.updateEmployeeDepartmentId(employeeIdValue);
            employeeRepository.deleteByEmployeeId(employeeIdValue);
        }
        else
            throw new EmployeeServiceException("Invalid Employee ID, not able to delete");

        return true;
    }

    @Override
    public boolean updateEmployee(EmployeeRequest employeeRequest) throws Exception {
        Integer employeeId = employeeRequest.getId();

        if (employeeId == null || employeeId < 0) {
            throw new EmployeeServiceException("Invalid Employee ID, not able to update" + employeeId);
        }

        Optional<Employee> employeeEntity = employeeRepository.findById(employeeId);
        employeeEntity.orElseThrow(() -> new EmployeeServiceException("No record or entity to update"));

        Employee employee = convertEmployeeDtoToEntity(employeeEntity.get(), employeeRequest);
        employee = employeeRepository.save(employee);

        return employee.getId() > 0;
    }

    private Employee convertEmployeeDtoToEntity(Employee employeeEntity, EmployeeRequest employeeRequest) {

        // Employee employeeEntity = new Employee();
        employeeEntity.setId(employeeRequest.getId());
        employeeEntity.setFirstname(employeeRequest.getFirstname());
        employeeEntity.setLastname(employeeRequest.getLastname());
        employeeEntity.setEmail(employeeRequest.getEmail());
        employeeEntity.setDob(employeeRequest.getDob());
        employeeEntity.setMobile(employeeRequest.getMobile());
        employeeEntity.setGender(employeeRequest.getGender());

        Address addressDto = employeeRequest.getAddress();
        com.assessment.employeeservice.entity.Address addressEntity = new com.assessment.employeeservice.entity.Address();
        addressEntity.setHouse_number(addressDto.getHouse_number());
        addressEntity.setStreet(addressDto.getStreet());
        addressEntity.setZipcode(addressDto.getZipcode());

        Optional<com.assessment.employeeservice.entity.Department> departmentOpt = departmentRepository.findByName(employeeRequest.getDepartment().getName());
        com.assessment.employeeservice.entity.Department departmentEntity;

        if (departmentOpt.isPresent()) {
            departmentEntity = departmentOpt.get();
        } else {
            Department department = employeeRequest.getDepartment();
            departmentEntity = new com.assessment.employeeservice.entity.Department();
            departmentEntity.setName(department.getName());
            departmentEntity.setSector(department.getSector());
        }

        employeeEntity.setAddress(addressEntity);
        employeeEntity.setDepartment(departmentEntity);

        return employeeEntity;
    }

    private static EmployeeRequest convertEmployeeEntityToDto(Employee employee) {
        EmployeeRequest dto = new EmployeeRequest();

        dto.setId(employee.getId());
        dto.setFirstname(employee.getFirstname());
        dto.setLastname(employee.getLastname());
        dto.setEmail(employee.getEmail());
        dto.setDob(employee.getDob());
        dto.setMobile(employee.getMobile());
        dto.setGender(employee.getGender());
        dto.setAge(calculateAge(employee.getDob()));

        Address address = new Address();
        com.assessment.employeeservice.entity.Address entityAddress = employee.getAddress();
        address.setHouse_number(entityAddress.getHouse_number());
        address.setStreet(entityAddress.getStreet());
        address.setZipcode(entityAddress.getZipcode());
        dto.setAddress(address);

        Department department = new Department();
        com.assessment.employeeservice.entity.Department entityDepartment = employee.getDepartment();
        department.setId(entityDepartment.getId());
        department.setName(entityDepartment.getName());
        department.setSector(entityDepartment.getSector());
        dto.setDepartment(department);

        return dto;
    }


}
