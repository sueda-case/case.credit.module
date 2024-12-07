package com.example.credit.module.service;

import com.example.credit.module.config.ConfigurationProperties;
import com.example.credit.module.model.Customer;
import com.example.credit.module.model.Loan;
import com.example.credit.module.model.LoanInstallment;
import com.example.credit.module.model.PaymentResponse;
import com.example.credit.module.repository.CustomerRepository;
import com.example.credit.module.repository.LoanInstallmentRepository;
import com.example.credit.module.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class PaymentServiceGeneric implements PaymentService{
    @Autowired
    LoanInstallmentRepository loanInstallmentRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ConfigurationProperties config;

    @Override
    public PaymentResponse payInstallment(long loanId,double amount) {
        PaymentResponse response = new PaymentResponse();
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isPresent()) {
            double leftAmount = amount;
            int paidInstallmentCount = 0;
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 2);

            List<LoanInstallment> payableInstallmentList = loanInstallmentRepository.findByDueDateBeforeAndIsPaidOrderByNumberAsc(calendar.getTime(), false);
            calendar = Calendar.getInstance();
            for (LoanInstallment tempInstallment : payableInstallmentList) {
                long dayBetween = ChronoUnit.DAYS.between(LocalDate.now(), tempInstallment.getDueDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate());
                double calculatedAmount = tempInstallment.getAmount();
                if (dayBetween < 0) {
                    calculatedAmount = calculatedAmount + (config.getPenaltyMultiplier() * tempInstallment.getAmount()*dayBetween);
                } else {
                    calculatedAmount = calculatedAmount - (config.getPenaltyMultiplier() * tempInstallment.getAmount()*dayBetween);
                }
                if (leftAmount >= calculatedAmount) {
                    tempInstallment.setPaidAmount(calculatedAmount);
                    tempInstallment.setPaid(true);
                    tempInstallment.setPaymentDate(calendar.getTime());
                    loanInstallmentRepository.save(tempInstallment);
                    leftAmount = leftAmount - calculatedAmount;
                    paidInstallmentCount++;

                } else {
                    break;
                }


            }
            response.setTotalAmount(amount - leftAmount);
            response.setCountOfPaidInstallment(paidInstallmentCount);
            List<LoanInstallment> listOfLoanInstallment = loanInstallmentRepository.findByLoanIdAndIsPaid(loanId, false);
            if (listOfLoanInstallment.isEmpty()) {
                response.setPaid(true);
                loan.get().setPaid(true);
                loanRepository.save(loan.get());
                Optional<Customer> customer=customerRepository.findById(loan.get().getCustomerId());
                customer.get().setUsedCreditLimit(customer.get().getUsedCreditLimit()-loan.get().getLoanAmount() );
                customerRepository.save(customer.get());
            }

       return response;
        } else {
            response.setCountOfPaidInstallment(0);
            response.setPaid(false);
            response.setTotalAmount(0);
            response.setMessage("Loan can not be found");
            return response;
        }


    }
}
