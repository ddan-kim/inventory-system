package com.inventory.api.stock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventory.domain.stock.entity.StockTransaction;
import com.inventory.domain.stock.enums.TransactionType;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {

	Page<StockTransaction> findByProductIdOrderByCreatedAtDesc(Long productId, Pageable pageable);

	Page<StockTransaction> findByProductIdAndTypeOrderByCreatedAtDesc(Long productId, TransactionType type, Pageable pageable);
}
