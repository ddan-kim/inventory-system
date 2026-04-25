package com.inventory.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 상품
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "상품을 찾을 수 없습니다."),
    PRODUCT_INVALID_NAME(HttpStatus.BAD_REQUEST, "P002", "상품명은 필수입니다."),
    PRODUCT_INVALID_PRODUCT_ID(HttpStatus.BAD_REQUEST, "P003", "상품 ID는 필수입니다."),

    // 카테고리
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CT001", "카테고리를 찾을 수 없습니다."),

    // 재고
    STOCK_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "S001", "재고가 부족합니다."),
    STOCK_INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "S002", "유효하지 않은 수량입니다."),
    STOCK_INVALID_AMOUNT(HttpStatus.BAD_REQUEST, "S003", "수량은 1 이상이어야 합니다."),

    // 공통
    CONCURRENCY_CONFLICT(HttpStatus.CONFLICT, "C001", "동시성 충돌이 발생했습니다. 다시 시도해주세요.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
