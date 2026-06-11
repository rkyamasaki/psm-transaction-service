package com.psm.transaction_service.service;

import com.psm.transaction_service.domain.dto.AccountTransactionData;
import com.psm.transaction_service.domain.entity.Account;
import com.psm.transaction_service.domain.entity.AccountBalance;
import com.psm.transaction_service.domain.entity.AccountTransaction;
import com.psm.transaction_service.domain.entity.OperationType;
import com.psm.transaction_service.domain.enums.OperationTypeEnum;
import com.psm.transaction_service.domain.operation.FinancialOperation;
import com.psm.transaction_service.exception.*;
import com.psm.transaction_service.repository.AccountBalanceRepository;
import com.psm.transaction_service.repository.AccountRepository;
import com.psm.transaction_service.repository.AccountTransactionRepository;
import com.psm.transaction_service.repository.OperationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class TransactionService {

    private final IdempotencyCacheService idempotencyCacheService;

    private final AccountRepository accountRepository;

    private final AccountBalanceRepository accountBalanceRepository;

    private final OperationRepository operationRepository;

    private final AccountTransactionRepository accountTransactionRepository;

    public TransactionService(
        IdempotencyCacheService idempotencyCacheService,
        AccountRepository accountRepository,
        AccountBalanceRepository accountBalanceRepository,
        OperationRepository operationRepository,
        AccountTransactionRepository accountTransactionRepository
    ) {
        this.idempotencyCacheService = idempotencyCacheService;
        this.accountRepository = accountRepository;
        this.accountBalanceRepository = accountBalanceRepository;
        this.operationRepository = operationRepository;
        this.accountTransactionRepository = accountTransactionRepository;
    }

    public AccountTransactionData createTransaction(AccountTransactionData accountTransactionData) {
        final String idempotencyKey = accountTransactionData.idempotencyKey();
        final Long accountId = accountTransactionData.accountId();
        final OperationTypeEnum operationTypeEnum = OperationTypeEnum
                .fromId(accountTransactionData.operationTypeId()).
                orElseThrow(() -> new InvalidOperationException(accountTransactionData.operationTypeId()));

        boolean isTransactionAlreadyDone = idempotencyCacheService.exists(accountId, idempotencyKey);
        if (isTransactionAlreadyDone) {
            throw new TransactionDuplicateException(idempotencyKey);
        }

        final Account account =  accountRepository.findById(accountTransactionData.accountId())
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        final AccountBalance accountBalance = accountBalanceRepository.findByAccountAccountId(accountId)
                .orElseThrow(() -> new AccountBalanceNotFoundException(accountId));

        final BigDecimal balanceAmount = accountBalance.getBalance();
        final FinancialOperation financialOperation = operationTypeEnum.getFinancialOperation();
        final BigDecimal transactionAmount = accountTransactionData.amount();
        final BigDecimal result = financialOperation.operation(balanceAmount, transactionAmount);

        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeBalanceException();
        }

        final OperationType operationType = operationRepository.findById(operationTypeEnum.getId())
                .orElseThrow(() -> new InvalidOperationException(operationTypeEnum.getId()));

        accountBalance.setBalance(result);
        accountBalanceRepository.save(accountBalance);

        final BigDecimal finalOperationAmount = financialOperation.retrieveTransactionValue(transactionAmount);
        final AccountTransaction accountTransaction = new AccountTransaction(account,operationType, finalOperationAmount);

        return AccountTransactionData.from(accountTransactionRepository.save(accountTransaction));
    }


}
