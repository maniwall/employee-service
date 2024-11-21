package com.assessment.employeeservice.controller;

import com.assessment.employeeservice.dto.EmployeeDTO;
import com.assessment.employeeservice.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/employee")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController (EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployees() {
        List<EmployeeDTO> employees = employeeService.getEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        boolean result = employeeService.createEmployee(employeeDTO);
        if(result)
            return new ResponseEntity<>("Employee Created Successfully !!", HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Employee Not Created!!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Integer employeeId) throws Exception {
        EmployeeDTO emp = employeeService.getEmployee(employeeId);
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer employeeId) throws Exception {
        boolean result = employeeService.deleteEmployee(employeeId);

        if(result)
            return new ResponseEntity<>("Employee Deleted Successfully!!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Employee Not Deleted !!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<String> updateEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) throws Exception{
        boolean result = employeeService.updateEmployee(employeeDTO);
        if(result)
//            return new ResponseEntity<>("Employee Not Updated!!", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("Employee Updated Successfully !!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Employee Not Updated!!", HttpStatus.BAD_REQUEST);
    }
}
