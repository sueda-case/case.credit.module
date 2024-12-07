package com.example.credit.module.repository;

import com.example.credit.module.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository  extends JpaRepository<Loan,Long> {
    List<Loan> findByCustomerId(Long customerId);

    // Additional custom queries for filters can be added here
    List<Loan> findByCustomerIdAndNumberOfInstallment(Long customerId, int numberOfInstallments);
    List<Loan> findByCustomerIdAndNumberOfInstallmentAndIsPaid(Long customerId, int numberOfInstallments, boolean isPaid);
    List<Loan> findByCustomerIdAndIsPaid(Long customerId,boolean isPaid);
}
