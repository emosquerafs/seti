package com.seti.challenge.franchiseservice.application.usecases.product;

import com.seti.challenge.franchiseservice.domain.gateway.ProductRepository;
import com.seti.challenge.franchiseservice.domain.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProductByIdUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductByIdUseCase getProductByIdUseCase;

    private Product testProduct;
    private UUID testProductId;

    @BeforeEach
    void setUp() {
        testProductId = UUID.randomUUID();
        testProduct = Product.builder()
            .id(testProductId)
            .name("Test Product")
            .stock(50)
            .branchId(UUID.randomUUID())
            .build();
    }

    @Test
    void execute_ShouldReturnProductWhenFound() {
        // Given
        when(productRepository.findById(testProductId))
            .thenReturn(Mono.just(testProduct));

        // When & Then
        StepVerifier.create(getProductByIdUseCase.execute(testProductId))
            .expectNext(testProduct)
            .verifyComplete();

        verify(productRepository, times(1)).findById(testProductId);
    }

    @Test
    void execute_ShouldReturnEmptyWhenProductNotFound() {
        // Given
        when(productRepository.findById(testProductId))
            .thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(getProductByIdUseCase.execute(testProductId))
            .verifyComplete();

        verify(productRepository, times(1)).findById(testProductId);
    }

    @Test
    void execute_ShouldHandleRepositoryError() {
        // Given
        RuntimeException error = new RuntimeException("Database error");
        when(productRepository.findById(testProductId))
            .thenReturn(Mono.error(error));

        // When & Then
        StepVerifier.create(getProductByIdUseCase.execute(testProductId))
            .expectError(RuntimeException.class)
            .verify();

        verify(productRepository, times(1)).findById(testProductId);
    }
}
