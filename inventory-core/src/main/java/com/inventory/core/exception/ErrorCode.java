package com.inventory.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 상품
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "상품을 찾을 수 없습니다."),

    // 카테고
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CT001", "카테고리를 찾을 수 없습니다."),


    // 재고
    STOCK_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "S001", "재고가 부족합니다."),
    STOCK_INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "S002", "유효하지 않은 수량입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
