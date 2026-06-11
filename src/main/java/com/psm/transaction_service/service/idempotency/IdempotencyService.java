package com.psm.transaction_service.service.idempotency;

import com.psm.transaction_service.repository.AccountTransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class IdempotencyService {

    private static String KEY_SEPARADOR = "-";

    private final IdempotencyCacheService idempotencyCacheService;

    private final AccountTransactionRepository accountTransactionRepository;

    public IdempotencyService(
        IdempotencyCacheService idempotencyCacheService,
        AccountTransactionRepository accountTransactionRepository

    ) {
        this.idempotencyCacheService = idempotencyCacheService;
        this.accountTransactionRepository = accountTransactionRepository;
    }

    public boolean exists(Long accountId, String idempotencyKey) {
        if (idempotencyCacheService.exists(createIdempotencyKey(accountId, idempotencyKey))) {
            return true;
        }
        return accountTransactionRepository.existsByAccountAccountIdAndIdempotencyKey(accountId, idempotencyKey);
    }

    public void addOnCache(Long accountId, String idempotencyKey) {
        idempotencyCacheService.save(createIdempotencyKey(accountId, idempotencyKey));
    }

    public static String createIdempotencyKey(Long accountId, String idempotencyKey) {
        return accountId.toString() +
                KEY_SEPARADOR +
                idempotencyKey;
    }

}
