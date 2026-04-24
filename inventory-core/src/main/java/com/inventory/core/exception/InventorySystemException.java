package com.inventory.core.exception;

import lombok.Getter;

@Getter
public class InventorySystemException extends RuntimeException {

    private final ErrorCode errorCode;

    public InventorySystemException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public InventorySystemException(ErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }
}
