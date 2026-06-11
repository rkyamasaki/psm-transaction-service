package com.psm.transaction_service.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class IdempotencyCacheService {

    private static String KEY_SEPARADOR = "-";

    private final Cache<String, Boolean> cache =
            Caffeine.newBuilder()
                    .expireAfterWrite(Duration.ofHours(24))
                    .maximumSize(100_000)
                    .build();

    public boolean exists(Long accountId, String idempotencyKey) {
        return cache.getIfPresent(createIdempotencyKey(accountId, idempotencyKey)) != null;
    }

    public void save(Long accountId, String idempotencyKey) {
        cache.put(createIdempotencyKey(accountId, idempotencyKey), Boolean.TRUE);
    }

    private String createIdempotencyKey(Long accountId, String idempotencyKey) {
        return accountId.toString() +
                KEY_SEPARADOR +
                idempotencyKey;

    }

}
