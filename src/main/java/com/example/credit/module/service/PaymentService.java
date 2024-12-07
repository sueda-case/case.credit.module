package com.example.credit.module.service;

import com.example.credit.module.model.PaymentResponse;

public interface PaymentService {
    public PaymentResponse payInstallment(long loanId, double amount);
}
