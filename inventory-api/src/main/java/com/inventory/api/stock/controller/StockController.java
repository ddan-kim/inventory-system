package com.inventory.api.stock.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "입출고 관리", description = "상품 입고/출고/이력 조회 API")
@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

}