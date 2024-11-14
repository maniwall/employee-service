package com.assessment.employeeservice.entity;

import com.assessment.employeeservice.utils.Gender;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate dob;
    private String mobile;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    // private int age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;

    public Employee(String firstname, String lastname, String email, LocalDate dob, String mobile, Gender gender, Address address, Department department) {
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
