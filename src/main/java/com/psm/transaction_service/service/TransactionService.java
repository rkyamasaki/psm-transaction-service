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
import com.psm.transaction_service.service.idempotency.IdempotencyService;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private final IdempotencyService idempotencyService;

    private final AccountRepository accountRepository;

    private final AccountBalanceRepository accountBalanceRepository;

    private final OperationRepository operationRepository;

    private final AccountTransactionRepository accountTransactionRepository;

    public TransactionService(
        IdempotencyService idempotencyService,
        AccountRepository accountRepository,
        AccountBalanceRepository accountBalanceRepository,
        OperationRepository operationRepository,
        AccountTransactionRepository accountTransactionRepository
    ) {
        this.idempotencyService = idempotencyService;
        this.accountRepository = accountRepository;
        this.accountBalanceRepository = accountBalanceRepository;
        this.operationRepository = operationRepository;
        this.accountTransactionRepository = accountTransactionRepository;
    }

    public AccountTransactionData createTransaction(AccountTransactionData accountTransactionData) {
        final String idempotencyKey = accountTransactionData.idempotencyKey();
        final Long accountId = accountTransactionData.accountId();
        validateRequiredData(idempotencyKey, accountId);

        final OperationTypeEnum operationTypeEnum = retrieveOperatorEnum(accountTransactionData);
        final Account account = retrieveAccount(accountTransactionData, accountId);
        final AccountBalance accountBalance = retrienveAccountBalance(accountId);
        final OperationType operationType = retrieveOperationType(operationTypeEnum);

        final BigDecimal balanceAmount = accountBalance.getBalance();
        final FinancialOperation financialOperation = operationTypeEnum.getFinancialOperation();
        final BigDecimal transactionAmount = accountTransactionData.amount();
        final BigDecimal resultAmount = financialOperation.operation(balanceAmount, transactionAmount);

        validateIfAccountHasBalanceToProceed(resultAmount);

        accountBalance.setBalance(resultAmount);
        accountBalanceRepository.save(accountBalance);

        final BigDecimal resultOperationAmount = financialOperation.retrieveTransactionValue(transactionAmount);
        final AccountTransaction accountTransaction = new AccountTransaction(account, operationType, resultOperationAmount, idempotencyKey);
        idempotencyService.addOnCache(accountId, idempotencyKey);
        return AccountTransactionData.from(accountTransactionRepository.save(accountTransaction));
    }

    private void validateIfAccountHasBalanceToProceed(BigDecimal resultAmount) {
        boolean isInsufficientAmountOnBalance = resultAmount.compareTo(BigDecimal.ZERO) < 0;
        if (isInsufficientAmountOnBalance) {
            throw new NegativeBalanceException();
        }
    }

    private OperationTypeEnum retrieveOperatorEnum(AccountTransactionData accountTransactionData) {
        return OperationTypeEnum
                .fromId(accountTransactionData.operationTypeId()).
                orElseThrow(() -> new InvalidOperationException(accountTransactionData.operationTypeId()));
    }

    private OperationType retrieveOperationType(OperationTypeEnum operationTypeEnum) {
        return operationRepository.findById(operationTypeEnum.getId())
                .orElseThrow(() -> new InvalidOperationException(operationTypeEnum.getId()));
    }

    private AccountBalance retrienveAccountBalance(Long accountId) {
        return accountBalanceRepository.findByAccountAccountId(accountId)
                .orElseThrow(() -> new AccountBalanceNotFoundException(accountId));
    }

    private Account retrieveAccount(AccountTransactionData accountTransactionData, Long accountId) {
        return accountRepository.findById(accountTransactionData.accountId())
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    private void validateRequiredData(String idempotencyKey, Long accountId) {
        if (StringUtils.isBlank(idempotencyKey)) {
            throw new IdempotencyKeyNotPresentException();
        }

        boolean isTransactionAlreadyDone = idempotencyService.exists(accountId, idempotencyKey);
        if (isTransactionAlreadyDone) {
            throw new TransactionDuplicateException(idempotencyKey);
        }
    }


}
