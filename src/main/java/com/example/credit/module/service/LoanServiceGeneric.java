package com.example.credit.module.service;

import com.example.credit.module.dto.LoanDto;
import com.example.credit.module.model.Customer;
import com.example.credit.module.model.Loan;
import com.example.credit.module.model.LoanInstallment;
import com.example.credit.module.model.Response;
import com.example.credit.module.repository.CustomerRepository;
import com.example.credit.module.repository.LoanInstallmentRepository;
import com.example.credit.module.repository.LoanRepository;
import com.example.credit.module.utility.ErrorCodes;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class LoanServiceGeneric implements LoanService{
 static Logger logger= LoggerFactory.getLogger(LoanServiceGeneric.class);
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    LoanInstallmentRepository loanInstallmentRepository;
    @Transactional
    @Override
    public Response createLoan(LoanDto loanDto)
    {
        Response response=new Response();
        Optional<Customer> customer=customerRepository.findById(loanDto.getCustomerId());
        if(customer.isEmpty())
        {
            logger.error("Customer with ID {} not found",loanDto.getCustomerId());
            response.setCode(ErrorCodes.CUSTOMER_NOT_FOUND.getCode());
            response.setMessage(ErrorCodes.CUSTOMER_NOT_FOUND.getMessage());
            return response;
        }

        boolean isCreditEnough=customerHasEnoughLimit(loanDto.getLoanAmount(), customer.get().getUsedCreditLimit(),customer.get().getCreditLimit());
        if(isCreditEnough)
        {
                Loan loan=new Loan();
                loan.setLoanAmount(loanDto.getLoanAmount());
                loan.setInterestRate(loanDto.getInterestRate());
                loan.setNumberOfInstallment(loanDto.getNumberOfInstallment());
                loan.setCustomerId(loanDto.getCustomerId());
                loan.setCreateDate(new Date());
                loan=loanRepository.save(loan);

                createLoanInstallment(loan);
        logger.info("Loan has been created for amount {} with {} installments ",loan.getLoanAmount(),loan.getNumberOfInstallment());
        Customer tempCust=customer.get();
            tempCust.setUsedCreditLimit(customer.get().getUsedCreditLimit()+loanDto.getLoanAmount());
        customerRepository.save(tempCust);
        }else{
            logger.info("Loan Request declined because of the low credit limit.");
            response.setCode(ErrorCodes.INSUFICENT_CREDIT.getCode());
            response.setMessage(ErrorCodes.INSUFICENT_CREDIT.getMessage());
            return response;
        }

        response.setCode(ErrorCodes.SUCCESS.getCode());
        response.setMessage(ErrorCodes.SUCCESS.getMessage());
        return response;
    }

    @Override
    public List<Loan> getLoansByCustomerId(Long customerId, Integer numberOfInstallments, Boolean isPaid) {
        if (numberOfInstallments != null && isPaid != null) {
            return loanRepository.findByCustomerIdAndNumberOfInstallmentAndIsPaid(customerId, numberOfInstallments, isPaid);
        } else if (numberOfInstallments != null) {
            return loanRepository.findByCustomerIdAndNumberOfInstallment(customerId, numberOfInstallments);
        } else if (isPaid != null) {
            return loanRepository.findByCustomerIdAndIsPaid(customerId, isPaid);
        } else {
            return loanRepository.findByCustomerId(customerId);
        }
    }

    @Override
    public List<LoanInstallment> getLoanInstalments(Long loanId) {
        return loanInstallmentRepository.findByLoanId(loanId);
    }

    private void createLoanInstallment(Loan loan)
    {

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(loan.getCreateDate());
        double installmentAmount=calculateInstallmentAmount(loan.getLoanAmount(),loan.getNumberOfInstallment(),loan.getInterestRate());
        for(int i=1; loan.getNumberOfInstallment() >= i;i++)
        {
            LoanInstallment loanInstallment=new LoanInstallment();
            calendar.add(Calendar.MONTH,1);
            calendar.set(Calendar.DAY_OF_MONTH,1);
            loanInstallment.setLoanId(loan.getId());
            loanInstallment.setNumber(i);
            loanInstallment.setAmount(installmentAmount);
            loanInstallment.setDueDate(calendar.getTime());
            loanInstallmentRepository.save(loanInstallment);
        }
    }

    @Override
    public boolean customerHasEnoughLimit(double amount,double customerUsedCreditLimit,double customerCreditLimit)
    {
        if(customerCreditLimit>(amount+customerUsedCreditLimit))
        {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public double calculateInstallmentAmount(double totalAmount,int numberOfInstallment,double interestRate)
    {
        return (totalAmount+(totalAmount*interestRate))/numberOfInstallment;

    }

    @Override
    public Customer getCustomer(Long customerId)
    {
        Optional<Customer> customer=customerRepository.findById(customerId);
        return customer.get();
    }
}
