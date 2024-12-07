package com.example.credit.module.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= NumberOfInstallmentValidator.class)
public @interface NumberOfInstalmentValidation {
    String message() default "Invalid value for number of instalments";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
