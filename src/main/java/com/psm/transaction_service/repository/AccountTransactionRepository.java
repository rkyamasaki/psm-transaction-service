package com.psm.transaction_service.repository;

import com.psm.transaction_service.domain.entity.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {

    List<AccountTransaction> findByAccountAccountId(Long accountId);

    boolean existsByAccountAccountIdAndIdempotencyKey(Long accountId, String idempotencyKey);

}
