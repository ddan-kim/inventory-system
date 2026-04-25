package com.inventory.api.concurrency;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.inventory.api.product.repository.ProductRepository;
import com.inventory.api.stock.dto.request.InboundRequest;
import com.inventory.api.stock.dto.request.OutboundRequest;
import com.inventory.api.stock.repository.StockTransactionRepository;
import com.inventory.api.stock.service.StockService;
import com.inventory.domain.product.entity.Product;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("동시성 제어 통합 테스트")
class ConcurrencyTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    @BeforeEach
    void setUp() {
        stockTransactionRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("동시에 10건의 입고 요청이 들어와도 재고 정합성 유지")
    void 동시에_입고_요청_정합성_보장() throws InterruptedException {
        // given
        Product product = productRepository.save(Product.create("테스트 상품", 0L));

        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.inbound(new InboundRequest(product.getId(), product.getName(), 10L, "입고"));
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        Product result = productRepository.findById(product.getId()).orElseThrow();
        assertThat(result.getQuantity()).isEqualTo(100L);

        long txCount = stockTransactionRepository.count();
        assertThat(txCount).isEqualTo(10);

        executorService.shutdown();
    }

    @Test
    @DisplayName("동시에 입고와 출고가 혼합되어도 재고 정합성 유지")
    void 동시에_입고_출고_정합성() throws InterruptedException {
        //given 초기 재고 500, 입고 50건(10번), 출고 50건(10)번
        Product product = productRepository.save(Product.create("혼합 테스트 상품", 500L));

        int inboundThreads = 50;
        int outboundThreads = 50;
        int totalThreads = inboundThreads + outboundThreads;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(totalThreads);
        AtomicInteger inboundSuccess = new AtomicInteger(0);
        AtomicInteger outboundSuccess = new AtomicInteger(0);

        // when
        for (int i = 0; i < inboundThreads; i++) {
            executorService.submit(() -> {
                try {
                    stockService.inbound(new InboundRequest(product.getId(), product.getName(), 10L, "동시 입고"));
                    inboundSuccess.incrementAndGet();
                } catch (Exception e) {
                    // ignore
                } finally {
                    latch.countDown();
                }
            });
        }

        for (int i = 0; i < outboundThreads; i++) {
            executorService.submit(() -> {
                try {
                    stockService.outbound(new OutboundRequest(product.getId(), 10L, "동시 출고"));
                    outboundSuccess.incrementAndGet();
                } catch (Exception e) {
                    // ignore
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        Product result = productRepository.findById(product.getId()).orElseThrow();
        long expectedQuantity = 500L + (inboundSuccess.get() * 10L) - (outboundSuccess.get() * 10L);
        assertThat(result.getQuantity()).isEqualTo(expectedQuantity);
        assertThat(result.getQuantity()).isGreaterThanOrEqualTo(0L);
        assertThat(inboundSuccess.get()).isEqualTo(inboundThreads);
    }

    @Test
    @DisplayName("재고보다 많은 동시 출고 요청 시 초과 요청은 실패")
    void 재고보다_많은_출고() throws InterruptedException {
        // given 재고 100개에 150건 출고 요청
        Product product = productRepository.save(Product.create("초과 출고 테스트 상품", 100L));

        int threadCount = 150;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.outbound(new OutboundRequest(product.getId(), 1L, "초과 출고"));
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        Product result = productRepository.findById(product.getId()).orElseThrow();
        assertThat(successCount.get()).isEqualTo(100);
        assertThat(failCount.get()).isEqualTo(50);
        assertThat(result.getQuantity()).isEqualTo(0L);
    }

}
