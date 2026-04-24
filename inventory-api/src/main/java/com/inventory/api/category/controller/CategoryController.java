package com.inventory.api.category.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "카테고리 관리")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
}
