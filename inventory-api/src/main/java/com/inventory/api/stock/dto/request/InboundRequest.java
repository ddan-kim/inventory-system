package com.inventory.api.stock.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "상품 ID (기존 상품 입고 시)", example = "1")
    private Long productId;

    @NotBlank(message = "상품명은 필수입니다.")
    @Schema(description = "상품명")
    private String name;

    @NotNull(message = "입고 수량은 필수입니다.")
    @Min(value = 1, message = "입고 수량은 1 이상이어야 합니다.")
    @Schema(description = "입고 수량", example = "100")
    private Long quantity;

    @Schema(description = "메모", example = "정기 입고")
    private String memo;
}
