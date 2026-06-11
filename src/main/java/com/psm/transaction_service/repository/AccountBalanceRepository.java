package com.psm.transaction_service.repository;

import com.psm.transaction_service.domain.entity.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long> {

    Optional<AccountBalance> findByAccountAccountId(Long accountId);

}
