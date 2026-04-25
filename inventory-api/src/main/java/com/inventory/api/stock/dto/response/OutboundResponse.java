package com.inventory.api.stock.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "출고 처리 응답")
public class OutboundResponse {

    @Schema(description = "상품 ID", example = "1")
    private Long productId;

    @Schema(description = "상품명", example = "베어링")
    private String productName;

    @Schema(description = "출고 수량", example = "30")
    private Long outboundQuantity;

    @Schema(description = "출고 전 재고", example = "150")
    private Long previousQuantity;

    @Schema(description = "출고 후 현재 재고", example = "120")
    private Long currentQuantity;


    public static OutboundResponse of(Long productId, String productName, Long outboundQuantity, Long previousQuantity, Long currentQuantity) {
        return new OutboundResponse(productId, productName, outboundQuantity, previousQuantity, currentQuantity);
    }
}
