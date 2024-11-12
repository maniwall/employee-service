package com.assessment.employeeservice.utils;

import com.fasterxml.jackson.databind.util.ClassUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EmployeeGenderValidator implements ConstraintValidator<ValidateGender, Enum<?>> {
    private Pattern pattern;

    @Override
    public void initialize(ValidateGender annotation) {
        try {
            pattern = Pattern.compile(annotation.regexp());
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Given regex is invalid", e);
        }
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        Matcher m = pattern.matcher(value.name());
        return m.matches();
    }

//    @Override
//    public boolean isValid(Gender gender, ConstraintValidatorContext constraintValidatorContext) {
//        return gender.ordinal() == 0 || gender.ordinal() == 1;
////        return gender.name().equalsIgnoreCase("Male") || gender.name().equalsIgnoreCase("Female");
//    }
}
