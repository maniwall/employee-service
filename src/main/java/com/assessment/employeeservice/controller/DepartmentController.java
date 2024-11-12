package com.assessment.employeeservice.controller;

import com.assessment.employeeservice.dto.Department;
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
    public ResponseEntity<List<Department>> getDepartments() {
        List<Department> departments = departmentService.getDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createDepartment(@RequestBody @Valid Department department) {
        boolean result = departmentService.createDepartment(department);
        if (result)
            return new ResponseEntity<>("Department Created Successfully !!", HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Department Not Created!!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Integer departmentId) throws Exception {
        boolean result = departmentService.deleteDepartment(departmentId);

        if (result)
            return new ResponseEntity<>("Department Deleted Successfully !!", HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Department Not Deleted !!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<String> updateDepartment(@RequestBody @Valid Department department) throws Exception {
        boolean result = departmentService.updateDepartment(department);
        if (result)
            return new ResponseEntity<>("Department Updated Successfully !!", HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Department Not updated!!", HttpStatus.BAD_REQUEST);
    }


}
