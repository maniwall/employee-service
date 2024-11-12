package com.assessment.employeeservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

//    @JsonIgnore()
    // @NotNull(message = "Enter valid Department id")
    private Integer id;

    @NotNull(message = "Department Name is missing")
    private String name;

    @NotNull(message = "Department Sector is missing")
    private String sector;

    public Department(String name, String sector) {
        this.name = name;
        this.sector = sector;
    }
}
