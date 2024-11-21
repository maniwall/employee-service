package com.assessment.employeeservice.service;

import com.assessment.employeeservice.dto.AddressDTO;
import com.assessment.employeeservice.dto.DepartmentDTO;
import com.assessment.employeeservice.dto.EmployeeDTO;
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
    public List<EmployeeDTO> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(EmployeeServiceImpl::convertEmployeeEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean createEmployee(EmployeeDTO employeeDTO) {
        Employee employeeEntity = new Employee();
        Employee emp = convertEmployeeDtoToEntity(employeeEntity, employeeDTO);
        emp = employeeRepository.save(emp);
        return emp.getId() > 0;
    }

    @Override
    public EmployeeDTO getEmployee(Integer empId) throws Exception {
        Optional<Employee> employeeOpt = employeeRepository.findById(empId);
        return employeeOpt.map(EmployeeServiceImpl::convertEmployeeEntityToDto).orElseThrow(() -> new EmployeeServiceException("No record or entity found to fetch"));
    }

    @Override
    @Transactional
    public boolean deleteEmployee(Integer employeeId) throws Exception {
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        if(employee.isPresent()){
            Integer employeeIdValue = employee.get().getId();
            employeeRepository.updateEmployeeDepartmentId(employeeIdValue);
            employeeRepository.deleteById(employeeIdValue);
            // employeeRepository.deleteByEmployeeId(employeeIdValue);
        }
        else
            throw new EmployeeServiceException("Invalid Employee ID, not able to delete");

        return true;
    }

    @Override
    @Transactional
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws Exception {
        Integer employeeId = employeeDTO.getId();

        if (employeeId == null || employeeId < 0) {
            throw new EmployeeServiceException("Invalid Employee ID, not able to update" + employeeId);
        }

        Optional<Employee> employeeEntity = employeeRepository.findById(employeeId);
        employeeEntity.orElseThrow(() -> new EmployeeServiceException("No record or entity to update"));

        Employee employee = convertEmployeeDtoToEntity(employeeEntity.get(), employeeDTO);
        employee = employeeRepository.save(employee);

        return employee.getId() > 0;
    }

    private Employee convertEmployeeDtoToEntity(Employee employeeEntity, EmployeeDTO employeeDTO) {

        // Employee employeeEntity = new Employee();
        employeeEntity.setId(employeeDTO.getId());
        employeeEntity.setFirstname(employeeDTO.getFirstname());
        employeeEntity.setLastname(employeeDTO.getLastname());
        employeeEntity.setEmail(employeeDTO.getEmail());
        employeeEntity.setDob(employeeDTO.getDob());
        employeeEntity.setMobile(employeeDTO.getMobile());
        employeeEntity.setGender(employeeDTO.getGender());

        AddressDTO addressDto = employeeDTO.getAddress();
        com.assessment.employeeservice.entity.Address addressEntity = new com.assessment.employeeservice.entity.Address();
        if(null != addressDto) {
            addressEntity.setHouse_number(addressDto.getHouse_number());
            addressEntity.setStreet(addressDto.getStreet());
            addressEntity.setZipcode(addressDto.getZipcode());
        }

        Optional<com.assessment.employeeservice.entity.Department> departmentOpt = departmentRepository.findByName(employeeDTO.getDepartment().getName());
        com.assessment.employeeservice.entity.Department departmentEntity;

        if (departmentOpt.isPresent()) {
            departmentEntity = departmentOpt.get();
        } else {
            DepartmentDTO departmentDTO = employeeDTO.getDepartment();
            departmentEntity = new com.assessment.employeeservice.entity.Department();
            departmentEntity.setName(departmentDTO.getName());
            departmentEntity.setSector(departmentDTO.getSector());
        }

        employeeEntity.setAddress(addressEntity);
        employeeEntity.setDepartment(departmentEntity);

        return employeeEntity;
    }

    private static EmployeeDTO convertEmployeeEntityToDto(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();

        dto.setId(employee.getId());
        dto.setFirstname(employee.getFirstname());
        dto.setLastname(employee.getLastname());
        dto.setEmail(employee.getEmail());
        dto.setDob(employee.getDob());
        dto.setMobile(employee.getMobile());
        dto.setGender(employee.getGender());
        dto.setAge(calculateAge(employee.getDob()));

        AddressDTO addressDTO = new AddressDTO();
        com.assessment.employeeservice.entity.Address entityAddress = employee.getAddress();
        if(null != entityAddress) {
            addressDTO.setHouse_number(entityAddress.getHouse_number());
            addressDTO.setStreet(entityAddress.getStreet());
            addressDTO.setZipcode(entityAddress.getZipcode());
            dto.setAddress(addressDTO);
        }

        DepartmentDTO departmentDTO = new DepartmentDTO();
        com.assessment.employeeservice.entity.Department entityDepartment = employee.getDepartment();
        departmentDTO.setId(entityDepartment.getId());
        departmentDTO.setName(entityDepartment.getName());
        departmentDTO.setSector(entityDepartment.getSector());
        dto.setDepartment(departmentDTO);

        return dto;
    }


}
