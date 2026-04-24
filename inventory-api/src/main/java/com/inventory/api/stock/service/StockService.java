package com.inventory.api.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.api.product.dto.response.ProductResponse;
import com.inventory.api.product.repository.ProductRepository;
import com.inventory.api.stock.dto.request.InboundRequest;
import com.inventory.api.stock.dto.request.OutboundRequest;
import com.inventory.api.stock.dto.response.InboundResponse;
import com.inventory.api.stock.dto.response.OutboundResponse;
import com.inventory.api.stock.repository.StockTransactionRepository;
import com.inventory.core.exception.ErrorCode;
import com.inventory.core.exception.InventorySystemException;
import com.inventory.domain.product.entity.Product;
import com.inventory.domain.stock.entity.StockTransaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {
	private final ProductRepository productRepository;
	private final StockTransactionRepository stockTransactionRepository;


	/**
	 * 입고 처리 - 등록되지 않은 상품일 경우 신규 상품으로 등록 후 입고 처리
	 *
	 * @param request 입고 요청
	 * @return
	 */
	@Transactional
	public InboundResponse inbound(InboundRequest request) {
		Product product = productRepository.findByIdForUpdate(request.getProductId()).orElse(null);

		boolean isNewProduct = (product == null);
		Long previousQuantity;

		if (isNewProduct) {
			product = Product.create(request.getName(), request.getQuantity());
			product = productRepository.save(product);
			log.info("신규 상품 등록 및 입고: name={}, quantity={}", product.getName(), product.getQuantity());
		} else {
			previousQuantity = product.getQuantity();
			product.increaseStock(request.getQuantity());
			log.info("입고 처리: productId={}, name={}, quantity={} -> {}", product.getId(), product.getName(), previousQuantity, product.getQuantity());
		}

		StockTransaction transaction = StockTransaction.createInbound(product.getId(), request.getQuantity(), request.getMemo());
		stockTransactionRepository.save(transaction);

		return InboundResponse.of(
			product.getId(),
			product.getName(),
			product.getQuantity(),
			isNewProduct
		);
	}

	/**
	 * 출고 처리 - 재고 수량은 음수가 될 수 없음
	 *
	 * @param request 출고 요청
	 * @return
	 */
	@Transactional
	public OutboundResponse outbound(OutboundRequest request) {
		Product product = productRepository.findByIdForUpdate(request.getProductId())
			.orElseThrow(() -> new InventorySystemException(ErrorCode.PRODUCT_NOT_FOUND,
				"상품을 찾을 수 없습니다. ID: " + request.getProductId()));

		Long previousQuantity = product.getQuantity();
		product.decreaseStock(request.getQuantity());

		log.info("출고 처리: productId={}, name={}, quantity={} -> {}", product.getId(), product.getName(), previousQuantity, product.getQuantity());

		StockTransaction transaction = StockTransaction.createOutbound(product.getId(), request.getQuantity(), request.getMemo());
		stockTransactionRepository.save(transaction);

		return OutboundResponse.of(
			product.getId(),
			product.getName(),
			request.getQuantity(),
			previousQuantity,
			product.getQuantity()
		);
	}

	@Transactional(readOnly = true)
	public ProductResponse getStock(Long productId) {
		Product product = productRepository.findById(productId).orElseThrow(() -> new InventorySystemException(ErrorCode.PRODUCT_NOT_FOUND, "상품을 찾을 수 없습니다. ID: " + productId));
		return ProductResponse.from(product);
	}

}
