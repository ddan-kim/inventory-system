package com.inventory.api.stock.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InboundRequest {

    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;

    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    @NotNull(message = "입고 수량은 필수입니다.")
    @Min(value = 1, message = "입고 수량은 1 이상이어야 합니다.")
    private Long quantity;

    private String memo;
}
