package com.assessment.employeeservice.service;

import com.assessment.employeeservice.dto.DepartmentDTO;
import com.assessment.employeeservice.repository.DepartmentRepository;
import com.assessment.employeeservice.utils.DepartmentServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
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
    public List<DepartmentDTO> getDepartments() {
        List<com.assessment.employeeservice.entity.Department> departmentEntities = departmentRepository.findAll();
        return departmentEntities.stream().map(DepartmentServiceImpl::convertDepartmentEntityToDto).collect(Collectors.toList());
    }

    @Override
    public boolean createDepartment(DepartmentDTO departmentDTO) throws Exception{
        com.assessment.employeeservice.entity.Department departmentEntity = convertDepartmentDtoToEntity(departmentDTO, new com.assessment.employeeservice.entity.Department());
        try{
            departmentEntity = departmentRepository.save(departmentEntity);
        } catch (Exception exception) {
            throw new DepartmentServiceException("Department already exists with mentioned Department name");
        }

        return departmentEntity.getId() > 0;
    }

    @Override
    public boolean deleteDepartment(Integer departmentId) throws Exception {
        Optional<com.assessment.employeeservice.entity.Department> departmentOpt = departmentRepository.findById(departmentId);
        if(departmentOpt.isPresent())
            departmentRepository.deleteById(departmentOpt.get().getId());
        else
            throw new DepartmentServiceException("Invalid DepartmentDTO ID, not able to delete");

        return true;
    }

    @Override
    public boolean updateDepartment(DepartmentDTO departmentDTO) throws Exception{

        if(null == departmentDTO.getId())
            throw new DepartmentServiceException("Enter valid DepartmentDTO ID");

        Optional<com.assessment.employeeservice.entity.Department> departmentEntityOpt = departmentRepository.findById(departmentDTO.getId());
        com.assessment.employeeservice.entity.Department departmentEntity;

        if(departmentEntityOpt.isPresent()) {
            departmentEntity= convertDepartmentDtoToEntity(departmentDTO, departmentEntityOpt.get());
            departmentRepository.save(departmentEntity);
            return true;
        } else
            throw new DepartmentServiceException("DepartmentDTO Entity not found to update, Invalid Data");
    }

    private static com.assessment.employeeservice.entity.Department convertDepartmentDtoToEntity(DepartmentDTO departmentDTO, com.assessment.employeeservice.entity.Department departmentEntity1) {
        return new com.assessment.employeeservice.entity.Department(departmentDTO.getId(), departmentDTO.getName(), departmentDTO.getSector());
    }

    private static DepartmentDTO convertDepartmentEntityToDto(com.assessment.employeeservice.entity.Department entity) {
        return new DepartmentDTO(entity.getId(), entity.getName(), entity.getSector());
    }
}
