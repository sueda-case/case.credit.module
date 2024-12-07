package com.example.credit.module.dto;

import com.example.credit.module.validator.InterestRateValidation;
import com.example.credit.module.validator.NumberOfInstalmentValidation;
import jakarta.validation.constraints.DecimalMin;


public class LoanDto {
    private Long customerId;
    @DecimalMin(value = "0", inclusive = false,message = "Loan amount must be greater than 0.")
    private double loanAmount;
    @NumberOfInstalmentValidation
    private int numberOfInstallment;
    @InterestRateValidation
    private double interestRate;


    public double getLoanAmount()
    {
        return loanAmount;
    }
    public void setLoanAmount(double loanAmount)
    {
        this.loanAmount=loanAmount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getNumberOfInstallment() {
        return numberOfInstallment;
    }

    public void setNumberofInstallment(int numberOfInstallment)
    {
        this.numberOfInstallment=numberOfInstallment;
    }
}
