package com.inventory.domain.stock.entity;

import com.inventory.domain.product.entity.Product;
import com.inventory.domain.stock.enums.TransactionType;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_transactions")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

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
    private StockTransaction(Product product, TransactionType type, Long quantity, String memo) {
        this.product = product;
        this.type = type;
        this.quantity = quantity;
        this.memo = memo;
    }

    public static StockTransaction createInbound(Product product, Long quantity, String memo) {
        return StockTransaction.builder()
            .product(product)
            .type(TransactionType.INBOUND)
            .quantity(quantity)
            .memo(memo)
            .build();
    }

    public static StockTransaction createOutbound(Product product, Long quantity, String memo) {
        return StockTransaction.builder()
            .product(product)
            .type(TransactionType.OUTBOUND)
            .quantity(quantity)
            .memo(memo)
            .build();
    }
}