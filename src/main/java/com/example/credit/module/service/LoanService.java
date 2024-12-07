package com.example.credit.module.service;

import com.example.credit.module.dto.LoanDto;
import com.example.credit.module.model.Customer;
import com.example.credit.module.model.Loan;
import com.example.credit.module.model.LoanInstallment;
import com.example.credit.module.model.Response;

import java.util.List;

public interface LoanService {
    public double calculateInstallmentAmount(double totalAmount,int numberOfInstallment,double interestRate);
    public boolean customerHasEnoughLimit(double amount,double customerUsedCreditLimit,double customerCreditLimit);
    public Response createLoan(LoanDto loanDto);
    public List<Loan> getLoansByCustomerId(Long customerId, Integer numberOfInstallments, Boolean isPaid);

    public List<LoanInstallment> getLoanInstalments(Long loanId);
    public Customer getCustomer(Long customerId);
}
