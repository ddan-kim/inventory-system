package com.inventory.api.product.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.inventory.api.product.repository.ProductRepository;
import com.inventory.api.stock.dto.request.InboundRequest;
import com.inventory.api.stock.dto.request.OutboundRequest;
import com.inventory.api.stock.dto.response.InboundResponse;
import com.inventory.api.stock.dto.response.OutboundResponse;
import com.inventory.api.stock.repository.StockTransactionRepository;
import com.inventory.api.stock.service.StockService;
import com.inventory.core.exception.InventorySystemException;
import com.inventory.domain.product.entity.Product;
import com.inventory.domain.stock.entity.StockTransaction;

@ExtendWith(MockitoExtension.class)
@DisplayName("StockService 단위 테스트")
class StockServiceTest {

    @InjectMocks
    private StockService stockService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockTransactionRepository stockTransactionRepository;

    @Nested
    @DisplayName("입고 처리")
    class Inbound {

        @Test
        @DisplayName("신규 상품 입고 시 상품을 등록하고 입고 처리한다")
        void inboundNewProduct() {
            // given: productId가 null → 신규 등록 분기
            InboundRequest request = new InboundRequest(null, "신규 상품", 100L, "최초 입고");
            Product savedProduct = createProductWithId(1L, "신규 상품", 100L);

            given(productRepository.save(any(Product.class))).willReturn(savedProduct);
            given(stockTransactionRepository.save(any(StockTransaction.class)))
                .willReturn(mock(StockTransaction.class));

            // when
            InboundResponse response = stockService.inbound(request);

            // then
            assertThat(response.isNewProduct()).isTrue();
            assertThat(response.getProductName()).isEqualTo("신규 상품");
            assertThat(response.getCurrentQuantity()).isEqualTo(100L);
        }

        @Test
        @DisplayName("기존 상품 입고 시 재고를 증가시킨다")
        void inboundExistingProduct() {
            // given: productId가 있음 → findByIdForUpdate로 조회 후 increaseStock
            InboundRequest request = new InboundRequest(1L, "기존 상품", 50L, "추가 입고");
            Product existingProduct = createProductWithId(1L, "기존 상품", 100L);

            given(productRepository.findByIdForUpdate(1L)).willReturn(Optional.of(existingProduct));
            given(stockTransactionRepository.save(any(StockTransaction.class)))
                .willReturn(mock(StockTransaction.class));

            // when
            InboundResponse response = stockService.inbound(request);

            // then: 100 + 50 = 150
            assertThat(response.isNewProduct()).isFalse();
            assertThat(response.getCurrentQuantity()).isEqualTo(150L);
        }
    }

    @Nested
    @DisplayName("출고 처리")
    class Outbound {

        @Test
        @DisplayName("정상적으로 출고 처리한다")
        void outboundSuccess() {
            // given
            OutboundRequest request = new OutboundRequest(1L, 30L, "주문 출고");
            Product product = createProductWithId(1L, "상품 A", 100L);

            given(productRepository.findByIdForUpdate(1L)).willReturn(Optional.of(product));
            given(stockTransactionRepository.save(any(StockTransaction.class)))
                .willReturn(mock(StockTransaction.class));

            // when
            OutboundResponse response = stockService.outbound(request);

            // then: 100 - 30 = 70
            assertThat(response.getOutboundQuantity()).isEqualTo(30L);
            assertThat(response.getPreviousQuantity()).isEqualTo(100L);
            assertThat(response.getCurrentQuantity()).isEqualTo(70L);
        }

        @Test
        @DisplayName("존재하지 않는 상품 출고 시 예외를 발생시킨다")
        void outboundProductNotFound() {
            // given: 존재하지 않는 상품 ID
            OutboundRequest request = new OutboundRequest(999L, 10L, null);
            given(productRepository.findByIdForUpdate(999L)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> stockService.outbound(request))
                .isInstanceOf(InventorySystemException.class);
        }

        @Test
        @DisplayName("재고 부족 시 예외를 발생시킨다")
        void outboundInsufficientStock() {
            // given: 재고 100인데 150 출고 요청
            OutboundRequest request = new OutboundRequest(1L, 150L, null);
            Product product = createProductWithId(1L, "상품 A", 100L);

            given(productRepository.findByIdForUpdate(1L)).willReturn(Optional.of(product));

            // when & then: 도메인 엔티티에서 IllegalStateException 발생
            assertThatThrownBy(() -> stockService.outbound(request))
                .isInstanceOf(InventorySystemException.class)
                .hasMessageContaining("재고가 부족");
        }
    }

    /**
     * 테스트용 Product 생성
     */
    private Product createProductWithId(Long id, String name, Long quantity) {
        Product product = Product.create(name, quantity);
        ReflectionTestUtils.setField(product, "id", id);
        return product;
    }

}
