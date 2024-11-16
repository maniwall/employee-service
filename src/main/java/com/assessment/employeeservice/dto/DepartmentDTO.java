package com.assessment.employeeservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {

//    @JsonIgnore()
    // @NotNull(message = "Enter valid DepartmentDTO id")
    private Integer id;

    @NotNull(message = "DepartmentDTO Name is missing")
    private String name;

    @NotNull(message = "DepartmentDTO Sector is missing")
    private String sector;

    public DepartmentDTO(String name, String sector) {
        this.name = name;
        this.sector = sector;
    }
}
