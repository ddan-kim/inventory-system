package com.inventory.api.stock.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "입고 처리 응답")
public class InboundResponse {

    @Schema(description = "상품 ID", example = "1")
    private Long productId;

    @Schema(description = "상품명", example = "베어링")
    private String productName;

    @Schema(description = "입고 후 현재 재고", example = "100")
    private Long currentQuantity;

    @Schema(description = "신규 상품 여부", example = "true")
    private boolean newProduct;

    public static InboundResponse of(Long productId, String productName, Long quantity, boolean newProduct) {
        return new InboundResponse(productId, productName, quantity, newProduct);
    }
}
