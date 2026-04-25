package com.inventory.api.stock.dto.response;

import java.time.LocalDateTime;

import com.inventory.domain.stock.entity.StockTransaction;
import com.inventory.domain.stock.enums.TransactionType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "입출고 이력 응답")
public class StockTransactionResponse {

    @Schema(description = "이력 ID", example = "1")
    private Long id;

    @Schema(description = "상품 ID", example = "1")
    private Long productId;

    @Schema(description = "상품명", example = "베어링")
    private String productName;

    @Schema(description = "이력 타입", example = "INBOUND")
    private TransactionType type;

    @Schema(description = "이력 타입 설명", example = "입고")
    private String typeDescription;

    @Schema(description = "수량", example = "100")
    private Long quantity;

    @Schema(description = "메모", example = "정기 입고")
    private String memo;

    @Schema(description = "처리일시")
    private LocalDateTime createdAt;

    public static StockTransactionResponse of(StockTransaction transaction, String productName) {
        return new StockTransactionResponse(
            transaction.getId(),
            transaction.getProductId(),
            productName,
            transaction.getType(),
            transaction.getType().getDescription(),
            transaction.getQuantity(),
            transaction.getMemo(),
            transaction.getCreatedAt()
        );
    }
}
