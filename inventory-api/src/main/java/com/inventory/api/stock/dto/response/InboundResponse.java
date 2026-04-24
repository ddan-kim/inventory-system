package com.inventory.api.stock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InboundResponse {

    private Long productId;
    private String productCode;
    private String productName;
    private Long inboundQuantity;
    private Long previousQuantity;
    private Long currentQuantity;
    private boolean newProduct;

    public static InboundResponse of(Long productId, String productCode, String productName, Long inboundQuantity, Long previousQuantity, Long currentQuantity, boolean newProduct) {
        return new InboundResponse(productId, productCode, productName, inboundQuantity,
            previousQuantity, currentQuantity, newProduct);
    }
}
