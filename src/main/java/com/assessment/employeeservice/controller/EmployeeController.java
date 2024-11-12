package com.assessment.employeeservice.controller;

import com.assessment.employeeservice.dto.EmployeeRequest;
import com.assessment.employeeservice.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController (EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeRequest>> getEmployees() {
        List<EmployeeRequest> employees = employeeService.getEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) {
        boolean result = employeeService.createEmployee(employeeRequest);
        if(result)
            return new ResponseEntity<>("Employee Created Successfully !!", HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Employee Not Created!!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeRequest> getEmployee(@PathVariable Integer employeeId) throws Exception {
        EmployeeRequest emp = employeeService.getEmployee(employeeId);
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer employeeId) throws Exception {
        boolean result = employeeService.deleteEmployee(employeeId);

        if(result)
            return new ResponseEntity<>("Employee Deleted Successfully !!", HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Employee Not Deleted !!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<String> updateEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) throws Exception{
        boolean result = employeeService.updateEmployee(employeeRequest);
        if(result)
            return new ResponseEntity<>("Employee Updated Successfully !!", HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Employee Not Updated!!", HttpStatus.BAD_REQUEST);
    }
}
