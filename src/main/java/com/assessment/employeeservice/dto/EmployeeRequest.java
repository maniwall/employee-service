package com.assessment.employeeservice.dto;

import com.assessment.employeeservice.utils.Gender;
import com.assessment.employeeservice.utils.ValidateGender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private Integer id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 32, message = "FirstName must be between 3 and 32 characters")
    private String firstname;

    @Size(min = 3, max = 32, message = "LastName must be between 3 and 32 characters")
    private String lastname;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "DOB should be valid")
    private LocalDate dob;

    @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be valid")
    private String mobile;

    @NotNull(message = "Invalid gender")
    @ValidateGender(regexp = "MALE|FEMALE")
    private Gender gender;

    private int age;

    @NotNull(message = "Address is missing")
    private Address address;

    @NotNull(message = "Department is missing")
    private Department department;

    public EmployeeRequest(String firstname, String lastname, String email, LocalDate dob, String mobile, Gender gender, Address address, Department department) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.dob = dob;
        this.mobile = mobile;
        this.gender = gender;
        this.address = address;
        this.department = department;
    }
}
