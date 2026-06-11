package com.psm.transaction_service.service;

import com.psm.transaction_service.domain.dto.AccountData;
import com.psm.transaction_service.domain.entity.Account;
import com.psm.transaction_service.domain.entity.AccountBalance;
import com.psm.transaction_service.exception.AccountAlreadyExistsException;
import com.psm.transaction_service.exception.AccountNotFoundException;
import com.psm.transaction_service.exception.InvalidDocumentException;
import com.psm.transaction_service.repository.AccountBalanceRepository;
import com.psm.transaction_service.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountBalanceRepository accountBalanceRepository;

    public AccountService(
            AccountRepository accountRepository,
            AccountBalanceRepository accountBalanceRepository
    ) {
        this.accountRepository = accountRepository;
        this.accountBalanceRepository = accountBalanceRepository;
    }

    public AccountData createAccount(AccountData accountData) {
        final String documentNumber = accountData.documentNumber();

        if (!isValidNumber(documentNumber)) {
            throw new InvalidDocumentException(documentNumber);
        }

        if (accountRepository.existsByDocumentNumber(documentNumber)) {
            throw new AccountAlreadyExistsException(documentNumber);
        }

        Account createdAccount = accountRepository.save(new Account(documentNumber));
        createAccountBalance(createdAccount);
        return AccountData.from(createdAccount);
    }

    public AccountData findAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        return AccountData.from(account);
    }

    private void createAccountBalance(Account account) {
        final AccountBalance accountBalance = new AccountBalance(account);
        accountBalanceRepository.save(accountBalance);
    }

    private boolean isValidNumber(String value) {
        return value != null
                && value.matches("-?\\d+");
    }

}
