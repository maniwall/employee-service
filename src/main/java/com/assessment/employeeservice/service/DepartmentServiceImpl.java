package com.assessment.employeeservice.service;

import com.assessment.employeeservice.dto.Department;
import com.assessment.employeeservice.repository.DepartmentRepository;
import com.assessment.employeeservice.utils.DepartmentServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getDepartments() {
        List<com.assessment.employeeservice.entity.Department> departmentEntities = departmentRepository.findAll();
        return departmentEntities.stream().map(DepartmentServiceImpl::convertDepartmentEntityToDto).collect(Collectors.toList());
    }

    @Override
    public boolean createDepartment(Department department) {
        com.assessment.employeeservice.entity.Department departmentEntity = convertDepartmentDtoToEntity(department);
        departmentEntity = departmentRepository.save(departmentEntity);
        return departmentEntity.getId() > 0;
    }

    @Override
    public boolean deleteDepartment(Integer departmentId) throws Exception {
        Optional<com.assessment.employeeservice.entity.Department> departmentOpt = departmentRepository.findById(departmentId);
        if(departmentOpt.isPresent())
            departmentRepository.deleteById(departmentOpt.get().getId());
        else
            throw new DepartmentServiceException("Invalid Department ID, not able to delete");

        return true;
    }

    private static com.assessment.employeeservice.entity.Department convertDepartmentDtoToEntity(Department department) {
        return new com.assessment.employeeservice.entity.Department(department.getName(), department.getSector());
    }

    private static Department convertDepartmentEntityToDto(com.assessment.employeeservice.entity.Department entity) {
        return new Department(entity.getId(), entity.getName(), entity.getSector());
    }
}
