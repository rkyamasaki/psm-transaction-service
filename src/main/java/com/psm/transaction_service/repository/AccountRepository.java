package com.psm.transaction_service.repository;

import com.psm.transaction_service.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByDocumentNumber(String documentNumber);

}
