package com.example.credit.module.controller;

import com.example.credit.module.dto.LoanDto;
import com.example.credit.module.model.*;
import com.example.credit.module.service.LoanService;
import com.example.credit.module.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    @Autowired
    LoanService loanService;
    @Autowired
    PaymentService paymentService;

    @PostMapping("/admin/apply")
    public ResponseEntity<Response> createLoan(@Validated @RequestBody LoanDto loan) {

            Response response=loanService.createLoan(loan);
            return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }

    @PutMapping("/admin/pay/{loanId}/{amount}")
    public ResponseEntity<PaymentResponse> payInstallment(
            @PathVariable Long loanId,
            @PathVariable double amount)
    {
      PaymentResponse response=  paymentService.payInstallment(loanId,amount);
      return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/loan/{customerId}")
    public ResponseEntity<List<Loan>> getLoans(
            @PathVariable Long customerId,
            @RequestParam(required = false) Integer numberOfInstallments,
            @RequestParam(required = false) Boolean isPaid){
        List<Loan> loans=loanService.getLoansByCustomerId(customerId, numberOfInstallments, isPaid);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/admin/loanInstallment/{loanId}")
    public ResponseEntity<List<LoanInstallment>> getLoanInstallments(
            @PathVariable Long loanId
           ){
        List<LoanInstallment> loanInstallment=loanService.getLoanInstalments(loanId);
        return ResponseEntity.ok(loanInstallment);
    }

    @GetMapping("/admin/customer/{customerId}")
    public ResponseEntity<Customer> getCustomer(
            @PathVariable Long customerId
    ){
        Customer loanInstallment=loanService.getCustomer(customerId);
        return ResponseEntity.ok(loanInstallment);
    }

}
