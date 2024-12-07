package com.example.credit.module.validator;

import com.example.credit.module.config.ConfigurationProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class NumberOfInstallmentValidator implements ConstraintValidator<NumberOfInstalmentValidation, Integer> {
    List<Integer> listOfAllowedInstallmentCount;
    public NumberOfInstallmentValidator(ConfigurationProperties config) {
        this.listOfAllowedInstallmentCount = config.getAllowedInstallments();
    }
    
    @Override
    public void initialize(NumberOfInstalmentValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer i, ConstraintValidatorContext constraintValidatorContext) {
        if(listOfAllowedInstallmentCount.contains(i))
        {
            return true;
        }
        else {
            return false;
        }
    }
}
