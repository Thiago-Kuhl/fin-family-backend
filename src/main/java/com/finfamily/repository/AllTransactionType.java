package com.finfamily.repository;

import com.finfamily.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllTransactionType extends JpaRepository<TransactionType, Integer> {
}
