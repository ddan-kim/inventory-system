package com.inventory.api.stock.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OutboundRequest {

    @NotNull(message = "상품 ID는 필수입니다.")
    @Schema(description = "상품 ID", example = "1")
    private Long productId;

    @NotNull(message = "출고 수량은 필수입니다.")
    @Min(value = 1, message = "출고 수량은 1 이상이어야 합니다.")
    @Schema(description = "출고 수량", example = "30")
    private Long quantity;

    @Schema(description = "메모", example = "출고")
    private String memo;
}
