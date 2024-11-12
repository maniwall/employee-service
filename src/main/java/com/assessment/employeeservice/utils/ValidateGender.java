package com.assessment.employeeservice.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmployeeGenderValidator.class)
public @interface ValidateGender {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public String message() default "Not valid Employee Gender";

    String regexp();
}
