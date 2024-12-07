package com.example.credit.module;

import com.example.credit.module.dto.LoanDto;
import com.example.credit.module.model.Customer;
import com.example.credit.module.model.Loan;
import com.example.credit.module.model.LoanInstallment;
import com.example.credit.module.model.Response;
import com.example.credit.module.repository.CustomerRepository;
import com.example.credit.module.repository.LoanInstallmentRepository;
import com.example.credit.module.repository.LoanRepository;
import com.example.credit.module.service.LoanServiceGeneric;
import com.example.credit.module.utility.ErrorCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class LoanServiceGenericTest {

    @InjectMocks
    private LoanServiceGeneric loanService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanInstallmentRepository loanInstallmentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLoan_Success() {
        // Arrange
        LoanDto loanDto = new LoanDto();
        loanDto.setCustomerId(1L);
        loanDto.setLoanAmount(1000.0);
        loanDto.setNumberofInstallment(12);
        loanDto.setInterestRate(0.05);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCreditLimit(5000.0);
        customer.setUsedCreditLimit(2000.0);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(loanInstallmentRepository.save(any(LoanInstallment.class))).thenReturn(new LoanInstallment());

        // Act
        Response result = loanService.createLoan(loanDto);

        // Assert
        assertEquals(ErrorCodes.SUCCESS.getCode(),result.getCode());
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(loanRepository, times(1)).save(any(Loan.class));
        verify(loanInstallmentRepository, times(12)).save(any(LoanInstallment.class)); // Check that 12 installments are created
    }

    @Test
    void testCreateLoan_CustomerNotFound() {
        // Arrange
        LoanDto loanDto = new LoanDto();
        loanDto.setCustomerId(1L);
        loanDto.setLoanAmount(1000.0);
        loanDto.setNumberofInstallment(12);
        loanDto.setInterestRate(0.05);

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Response result = loanService.createLoan(loanDto);

        // Assert
        assertEquals(ErrorCodes.CUSTOMER_NOT_FOUND.getCode(),result.getCode());
        verify(customerRepository, times(1)).findById(1L);
        verifyNoInteractions(loanRepository);
        verifyNoInteractions(loanInstallmentRepository);
    }

    @Test
    void testCustomerHasEnoughLimit_EnoughCredit() {
        // Act
        boolean result = loanService.customerHasEnoughLimit(1000.0, 2000.0, 5000.0);

        // Assert
        assertTrue(result);
    }

    @Test
    void testCustomerHasEnoughLimit_NotEnoughCredit() {
        // Act
        boolean result = loanService.customerHasEnoughLimit(3000.0, 2000.0, 5000.0);

        // Assert
        assertFalse(result);
    }

    @Test
    void testGetLoansByCustomerId() {
        // Arrange
        Long customerId = 1L;
        Loan loan1 = new Loan();
        loan1.setCustomerId(customerId);
        loan1.setLoanAmount(1000.0);

        Loan loan2 = new Loan();
        loan2.setCustomerId(customerId);
        loan2.setLoanAmount(2000.0);

        when(loanRepository.findByCustomerId(customerId)).thenReturn(Arrays.asList(loan1, loan2));

        // Act
        List<Loan> result = loanService.getLoansByCustomerId(customerId, null, null);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(loanRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void testGetLoanInstalments() {
        // Arrange
        Long loanId = 1L;
        LoanInstallment installment1 = new LoanInstallment();
        installment1.setLoanId(loanId);
        installment1.setNumber(1);
        installment1.setAmount(100.0);

        LoanInstallment installment2 = new LoanInstallment();
        installment2.setLoanId(loanId);
        installment2.setNumber(2);
        installment2.setAmount(100.0);

        when(loanInstallmentRepository.findByLoanId(loanId)).thenReturn(Arrays.asList(installment1, installment2));

        // Act
        List<LoanInstallment> result = loanService.getLoanInstalments(loanId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(loanInstallmentRepository, times(1)).findByLoanId(loanId);
    }
}