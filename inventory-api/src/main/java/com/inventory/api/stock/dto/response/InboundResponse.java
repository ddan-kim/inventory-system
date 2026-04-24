package com.inventory.api.stock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InboundResponse {

    private Long productId;
    private String productName;
    private Long quantity;
    private boolean newProduct;

    public static InboundResponse of(Long productId, String productName, Long quantity, boolean newProduct) {
        return new InboundResponse(productId, productName, quantity, newProduct);
    }
}
