package com.example.credit.module.repository;

import com.example.credit.module.model.Loan;
import com.example.credit.module.model.LoanInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment,Long> {
    List<LoanInstallment> findByLoanId(Long loanId);

    List<LoanInstallment>findByDueDateBeforeAndIsPaidOrderByNumberAsc(Date dueDate,boolean isPaid);

    List<LoanInstallment> findByLoanIdAndIsPaid(long loanId,boolean isPaid);
}
