package com.seti.challenge.franchiseservice.application.usecases.product;

import com.seti.challenge.franchiseservice.domain.gateway.ProductRepository;
import com.seti.challenge.franchiseservice.domain.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProductWithMaxStockByBranchUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductWithMaxStockByBranchUseCase getProductWithMaxStockByBranchUseCase;

    private UUID testBranchId;
    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    void setUp() {
        testBranchId = UUID.randomUUID();
        
        product1 = Product.builder()
            .id(UUID.randomUUID())
            .name("Product 1")
            .stock(10)
            .branchId(testBranchId)
            .build();
            
        product2 = Product.builder()
            .id(UUID.randomUUID())
            .name("Product 2")
            .stock(50)
            .branchId(testBranchId)
            .build();
            
        product3 = Product.builder()
            .id(UUID.randomUUID())
            .name("Product 3")
            .stock(25)
            .branchId(testBranchId)
            .build();
    }

    @Test
    void execute_ShouldReturnProductWithMaxStock() {
        // Given
        when(productRepository.findByBranchId(testBranchId))
            .thenReturn(Flux.just(product1, product2, product3));

        // When & Then
        StepVerifier.create(getProductWithMaxStockByBranchUseCase.execute(testBranchId))
            .expectNext(product2) // Product with stock 50
            .verifyComplete();

        verify(productRepository, times(1)).findByBranchId(testBranchId);
    }

    @Test
    void execute_ShouldReturnEmptyWhenNoProducts() {
        // Given
        when(productRepository.findByBranchId(testBranchId))
            .thenReturn(Flux.empty());

        // When & Then
        StepVerifier.create(getProductWithMaxStockByBranchUseCase.execute(testBranchId))
            .verifyComplete();

        verify(productRepository, times(1)).findByBranchId(testBranchId);
    }

    @Test
    void execute_ShouldHandleRepositoryError() {
        // Given
        RuntimeException error = new RuntimeException("Repository error");
        when(productRepository.findByBranchId(testBranchId))
            .thenReturn(Flux.error(error));

        // When & Then
        StepVerifier.create(getProductWithMaxStockByBranchUseCase.execute(testBranchId))
            .expectError(RuntimeException.class)
            .verify();

        verify(productRepository, times(1)).findByBranchId(testBranchId);
    }

    @Test
    void execute_ShouldReturnFirstProductWhenSingleProduct() {
        // Given
        when(productRepository.findByBranchId(testBranchId))
            .thenReturn(Flux.just(product1));

        // When & Then
        StepVerifier.create(getProductWithMaxStockByBranchUseCase.execute(testBranchId))
            .expectNext(product1)
            .verifyComplete();

        verify(productRepository, times(1)).findByBranchId(testBranchId);
    }
}
