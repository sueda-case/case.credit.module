package com.example.credit.module.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=InterestRateValidator.class)
public @interface InterestRateValidation {
    String message() default "Invalid value for interest rate";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
