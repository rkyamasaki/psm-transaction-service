package com.psm.transaction_service.service;

import com.psm.transaction_service.domain.dto.AccountData;
import com.psm.transaction_service.domain.entity.Account;
import com.psm.transaction_service.domain.entity.AccountBalance;
import com.psm.transaction_service.exception.AccountAlreadyExistsException;
import com.psm.transaction_service.exception.AccountNotFoundException;
import com.psm.transaction_service.exception.InvalidDocumentException;
import com.psm.transaction_service.repository.AccountBalanceRepository;
import com.psm.transaction_service.repository.AccountRepository;
import com.psm.transaction_service.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountBalanceRepository accountBalanceRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldCreateAccount() {
        final String documentNumber = "12345678900";
        final AccountData accountData = new AccountData(Optional.empty(), documentNumber);

        when(accountRepository.existsByDocumentNumber(documentNumber))
                .thenReturn(false);

        Account savedAccount = createTestAccount(1L, documentNumber);

        when(accountRepository.save(any(Account.class)))
                .thenReturn(savedAccount);

        AccountBalance accountBalance = new AccountBalance(savedAccount);

        when(accountBalanceRepository.save(any(AccountBalance.class)))
                .thenReturn(accountBalance);

        AccountData result = accountService.createAccount(accountData);

        assertNotNull(result);
        assertEquals(documentNumber, result.documentNumber());

        verify(accountRepository).existsByDocumentNumber(documentNumber);
        verify(accountBalanceRepository).save(any(AccountBalance.class));
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenDocumentIsInvalid() {
        AccountData accountData = new AccountData(null, "abc123");

        assertThrows(
                InvalidDocumentException.class,
                () -> accountService.createAccount(accountData)
        );

        verify(accountRepository, never()).existsByDocumentNumber(anyString());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountAlreadyExists() {
        AccountData accountData = new AccountData(null, "12345678900");

        when(accountRepository.existsByDocumentNumber("12345678900"))
                .thenReturn(true);

        assertThrows(
                AccountAlreadyExistsException.class,
                () -> accountService.createAccount(accountData)
        );

        verify(accountRepository).existsByDocumentNumber("12345678900");
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void shouldFindAccountById() {
        Long accountId = 1L;
        Account account = createTestAccount(1L, "12345678900");

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        AccountData result = accountService.findAccountById(accountId);

        assertNotNull(result);
        assertEquals("12345678900", result.documentNumber());

        verify(accountRepository).findById(accountId);
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {
        Long accountId = 1L;

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> accountService.findAccountById(accountId)
        );

        verify(accountRepository).findById(accountId);
    }

    private Account createTestAccount(Long id, String document) {
        Account account = TestUtils.instantiate(Account.class);

        ReflectionTestUtils.setField(
                account,
                "accountId",
                id
        );

        ReflectionTestUtils.setField(
                account,
                "documentNumber",
                document
        );

        return account;
    }

}
