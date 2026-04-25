package com.inventory.domain.stock.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.inventory.core.exception.ErrorCode;
import com.inventory.core.exception.InventorySystemException;
import com.inventory.domain.stock.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_transactions")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType type;

    @Column(nullable = false)
    private Long quantity;

    @Column(length = 500)
    private String memo;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    private StockTransaction(Long productId, TransactionType type, Long quantity, String memo) {
        validateProductId(productId);
        validateQuantity(quantity);
        this.productId = productId;
        this.type = type;
        this.quantity = quantity;
        this.memo = memo;
    }

    public static StockTransaction createInbound(Long productId, Long quantity, String memo) {
        return StockTransaction.builder()
            .productId(productId)
            .type(TransactionType.INBOUND)
            .quantity(quantity)
            .memo(memo)
            .build();
    }

    public static StockTransaction createOutbound(Long productId, Long quantity, String memo) {
        return StockTransaction.builder()
            .productId(productId)
            .type(TransactionType.OUTBOUND)
            .quantity(quantity)
            .memo(memo)
            .build();
    }

    private void validateProductId(Long productId) {
        if (productId == null) {
            throw new InventorySystemException(ErrorCode.PRODUCT_INVALID_PRODUCT_ID);
        }
    }

    private void validateQuantity(Long quantity) {
        if (quantity == null || quantity <= 0) {
            throw new InventorySystemException(ErrorCode.STOCK_INVALID_QUANTITY);
        }
    }
}