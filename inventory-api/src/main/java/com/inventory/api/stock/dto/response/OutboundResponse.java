package com.inventory.api.stock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OutboundResponse {

    private Long productId;
    private String productName;
    private Long outboundQuantity;
    private Long previousQuantity;
    private Long currentQuantity;

    public static OutboundResponse of(Long productId, String productName, Long outboundQuantity, Long previousQuantity, Long currentQuantity) {
        return new OutboundResponse(productId, productName, outboundQuantity,
                previousQuantity, currentQuantity);
    }
}
