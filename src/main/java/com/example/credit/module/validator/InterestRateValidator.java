package com.example.credit.module.validator;

import com.example.credit.module.config.ConfigurationProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InterestRateValidator implements ConstraintValidator<InterestRateValidation,Double> {
    private double interestRateMin=0.1;
    private double interestRateMax=0.5;

    public InterestRateValidator(ConfigurationProperties config) {
        interestRateMin=config.getInterestRateMin();
        interestRateMax=config.getInterestRateMax();
    }

    @Override
    public void initialize(InterestRateValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Double interestRate, ConstraintValidatorContext constraintValidatorContext) {

        if(interestRate>interestRateMin&&interestRate<interestRateMax)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
