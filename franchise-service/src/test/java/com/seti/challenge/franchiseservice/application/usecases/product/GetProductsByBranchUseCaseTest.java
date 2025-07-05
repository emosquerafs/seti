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
class GetProductsByBranchUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductsByBranchUseCase getProductsByBranchUseCase;

    private UUID testBranchId;
    private Product product1;
    private Product product2;

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
            .stock(20)
            .branchId(testBranchId)
            .build();
    }

    @Test
    void execute_ShouldReturnProductsForBranch() {
        // Given
        when(productRepository.findByBranchId(testBranchId))
            .thenReturn(Flux.just(product1, product2));

        // When & Then
        StepVerifier.create(getProductsByBranchUseCase.execute(testBranchId))
            .expectNext(product1)
            .expectNext(product2)
            .verifyComplete();

        verify(productRepository, times(1)).findByBranchId(testBranchId);
    }

    @Test
    void execute_ShouldReturnEmptyWhenNoProducts() {
        // Given
        when(productRepository.findByBranchId(testBranchId))
            .thenReturn(Flux.empty());

        // When & Then
        StepVerifier.create(getProductsByBranchUseCase.execute(testBranchId))
            .verifyComplete();

        verify(productRepository, times(1)).findByBranchId(testBranchId);
    }

    @Test
    void execute_ShouldHandleRepositoryError() {
        // Given
        RuntimeException error = new RuntimeException("Database error");
        when(productRepository.findByBranchId(testBranchId))
            .thenReturn(Flux.error(error));

        // When & Then
        StepVerifier.create(getProductsByBranchUseCase.execute(testBranchId))
            .expectError(RuntimeException.class)
            .verify();

        verify(productRepository, times(1)).findByBranchId(testBranchId);
    }
}
