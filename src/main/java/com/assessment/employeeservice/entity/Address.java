package com.assessment.employeeservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String house_number;
    private String street;
    private String zipcode;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Address(String house_number, String street, String zipcode) {
//        this.id = id;
        this.house_number = house_number;
        this.street = street;
        this.zipcode = zipcode;
    }
}
