package com.inventory.api.stock.dto.response;

import java.time.LocalDateTime;

import com.inventory.domain.stock.entity.StockTransaction;
import com.inventory.domain.stock.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockTransactionResponse {

    private Long id;
    private Long productId;
    private String productName;
    private TransactionType type;
    private String typeDescription;
    private Long quantity;
    private String memo;
    private LocalDateTime createdAt;

    public static StockTransactionResponse from(StockTransaction transaction) {
        return new StockTransactionResponse(
                transaction.getId(),
                transaction.getProduct().getId(),
                transaction.getProduct().getName(),
                transaction.getType(),
                transaction.getType().getDescription(),
                transaction.getQuantity(),
                transaction.getMemo(),
                transaction.getCreatedAt()
        );
    }
}
