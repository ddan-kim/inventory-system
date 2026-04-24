package com.inventory.domain.stock.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {

    INBOUND("입고"),
    OUTBOUND("출고");

    private final String description;
}
