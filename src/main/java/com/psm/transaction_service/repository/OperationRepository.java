package com.psm.transaction_service.repository;

import com.psm.transaction_service.domain.entity.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<OperationType, Integer> {
}
