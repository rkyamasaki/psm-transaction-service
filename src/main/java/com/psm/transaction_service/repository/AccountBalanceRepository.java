package com.psm.transaction_service.repository;

import com.psm.transaction_service.domain.entity.AccountBalance;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<AccountBalance> findWithLockByAccountAccountId(Long accountId);

}
