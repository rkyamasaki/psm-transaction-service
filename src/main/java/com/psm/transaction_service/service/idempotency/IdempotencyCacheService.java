package com.psm.transaction_service.service.idempotency;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class IdempotencyCacheService {

    private final Cache<String, Boolean> cache =
            Caffeine.newBuilder()
                    .expireAfterWrite(Duration.ofHours(24))
                    .maximumSize(100_000)
                    .build();

    protected boolean exists(String idempotencyKey) {
        return cache.getIfPresent(idempotencyKey) != null;
    }

    protected void save(String idempotencyKey) {
        cache.put(idempotencyKey, Boolean.TRUE);
    }

}
