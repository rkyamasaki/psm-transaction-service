package com.psm.transaction_service.service;

import com.psm.transaction_service.domain.dto.AccountData;
import com.psm.transaction_service.domain.entity.Account;
import com.psm.transaction_service.exception.AccountAlreadyExistsException;
import com.psm.transaction_service.exception.AccountNotFoundException;
import com.psm.transaction_service.exception.InvalidDocumentException;
import com.psm.transaction_service.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(
            AccountRepository accountRepository
    ) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(AccountData accountData) {
        final String documentNumber = accountData.documentNumber();

        if (!isValidNumber(documentNumber)) {
            throw new InvalidDocumentException(documentNumber);
        }

        if (accountRepository.existsByDocumentNumber(documentNumber)) {
            throw new AccountAlreadyExistsException(documentNumber);
        }

        return accountRepository.save(new Account(documentNumber));
    }

    public Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    private boolean isValidNumber(String value) {
        return value != null
                && value.matches("-?\\d+");
    }

}
