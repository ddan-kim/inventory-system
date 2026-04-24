package com.inventory.api.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "상품 관리", description = "상품 조회, 상태변경 API")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
}
