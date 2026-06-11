package com.psm.transaction_service.service;

import com.psm.transaction_service.domain.dto.AccountTransactionData;
import com.psm.transaction_service.domain.entity.Account;
import com.psm.transaction_service.domain.entity.AccountBalance;
import com.psm.transaction_service.domain.entity.AccountTransaction;
import com.psm.transaction_service.domain.entity.OperationType;
import com.psm.transaction_service.exception.AccountNotFoundException;
import com.psm.transaction_service.exception.TransactionDuplicateException;
import com.psm.transaction_service.repository.AccountBalanceRepository;
import com.psm.transaction_service.repository.AccountRepository;
import com.psm.transaction_service.repository.AccountTransactionRepository;
import com.psm.transaction_service.repository.OperationRepository;
import com.psm.transaction_service.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private IdempotencyCacheService idempotencyCacheService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountBalanceRepository accountBalanceRepository;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private AccountTransactionRepository accountTransactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void shouldCreatePurchaseTransactionWithNegativeAmount() {
        var data = new AccountTransactionData(
                null,
                1L,
                "idem-123",
                1,
                new BigDecimal("100.00")
        );

        Account account = new Account("12345678900");
        AccountBalance accountBalance = accountBalance(new BigDecimal("500.00"));
        OperationType operationType = operationType(1, "PURCHASE");

        when(idempotencyCacheService.exists(1L, "idem-123"))
                .thenReturn(false);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(accountBalanceRepository.findByAccountAccountId(1L))
                .thenReturn(Optional.of(accountBalance));

        when(operationRepository.findById(1))
                .thenReturn(Optional.of(operationType));

        when(accountTransactionRepository.save(any(AccountTransaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AccountTransaction result =
                transactionService.createTransaction(data);

        assertEquals(new BigDecimal("-100.00"), result.getAmount());

        ArgumentCaptor<AccountTransaction> captor =
                ArgumentCaptor.forClass(AccountTransaction.class);

        verify(accountTransactionRepository).save(captor.capture());

        AccountTransaction savedTransaction = captor.getValue();

        assertEquals(account, savedTransaction.getAccount());
        assertEquals(operationType, savedTransaction.getOperationType());
        assertEquals(new BigDecimal("-100.00"), savedTransaction.getAmount());
    }

    @Test
    void shouldCreatePaymentTransactionWithPositiveAmount() {
        var data = new AccountTransactionData(
                null,
                1L,
                "idem-456",
                4,
                new BigDecimal("100.00")
        );

        Account account = new Account("12345678900");
        AccountBalance accountBalance = accountBalance(new BigDecimal("500.00"));
        OperationType operationType = operationType(4, "PAYMENT");

        when(idempotencyCacheService.exists(1L, "idem-456"))
                .thenReturn(false);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(accountBalanceRepository.findByAccountAccountId(1L))
                .thenReturn(Optional.of(accountBalance));

        when(operationRepository.findById(4))
                .thenReturn(Optional.of(operationType));

        when(accountTransactionRepository.save(any(AccountTransaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AccountTransaction result =
                transactionService.createTransaction(data);

        assertEquals(new BigDecimal("100.00"), result.getAmount());
        assertEquals(new BigDecimal("600.00"), accountBalance.getBalance());

        verify(accountBalanceRepository).save(accountBalance);
        verify(accountTransactionRepository).save(any(AccountTransaction.class));
    }

    @Test
    void shouldThrowExceptionWhenTransactionIsDuplicated() {
        var data = new AccountTransactionData(
                null,
                1L,
                "idem-duplicated",
                1,
                new BigDecimal("100.00")
        );

        when(idempotencyCacheService.exists(1L, "idem-duplicated"))
                .thenReturn(true);

        assertThrows(
                TransactionDuplicateException.class,
                () -> transactionService.createTransaction(data)
        );

        verifyNoInteractions(accountRepository);
        verifyNoInteractions(accountBalanceRepository);
        verifyNoInteractions(operationRepository);
        verifyNoInteractions(accountTransactionRepository);
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {
        var data = new AccountTransactionData(
                null,
                999L,
                "idem-789",
                1,
                new BigDecimal("100.00")
        );

        when(idempotencyCacheService.exists(999L, "idem-789"))
                .thenReturn(false);

        when(accountRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> transactionService.createTransaction(data)
        );

        verify(accountTransactionRepository, never())
                .save(any(AccountTransaction.class));
    }

    private AccountBalance accountBalance(BigDecimal balance) {
        AccountBalance accountBalance = TestUtils.instantiate(AccountBalance.class);
        accountBalance.setBalance(balance);
        return accountBalance;
    }

    private OperationType operationType(Integer id, String description) {
        OperationType operationType = TestUtils.instantiate(OperationType.class);

        ReflectionTestUtils.setField(
                operationType,
                "operationTypeId",
                id
        );

        ReflectionTestUtils.setField(
                operationType,
                "description",
                description
        );

        return operationType;
    }

}