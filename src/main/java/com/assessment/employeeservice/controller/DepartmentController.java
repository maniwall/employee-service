package com.assessment.employeeservice.controller;

import com.assessment.employeeservice.dto.DepartmentDTO;
import com.assessment.employeeservice.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getDepartments() {
        List<DepartmentDTO> departmentDTOS = departmentService.getDepartments();
        return new ResponseEntity<>(departmentDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createDepartment(@RequestBody @Valid DepartmentDTO departmentDTO) throws Exception{
        boolean result = departmentService.createDepartment(departmentDTO);
        if (result)
            return new ResponseEntity<>("DepartmentDTO Created Successfully !!", HttpStatus.CREATED);
        else
            return new ResponseEntity<>("DepartmentDTO Not Created!!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Integer departmentId) throws Exception {
        boolean result = departmentService.deleteDepartment(departmentId);

        if (result)
            return new ResponseEntity<>("DepartmentDTO Deleted Successfully !!", HttpStatus.OK);
        else
            return new ResponseEntity<>("DepartmentDTO Not Deleted !!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<String> updateDepartment(@RequestBody @Valid DepartmentDTO departmentDTO) throws Exception {
        boolean result = departmentService.updateDepartment(departmentDTO);
        if (result)
            return new ResponseEntity<>("DepartmentDTO Updated Successfully !!", HttpStatus.OK);
        else
            return new ResponseEntity<>("DepartmentDTO Not updated!!", HttpStatus.BAD_REQUEST);
    }


}
