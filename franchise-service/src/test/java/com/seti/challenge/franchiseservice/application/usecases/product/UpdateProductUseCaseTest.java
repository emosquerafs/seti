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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductUseCase updateProductUseCase;

    private Product testProduct;
    private UUID testProductId;

    @BeforeEach
    void setUp() {
        testProductId = UUID.randomUUID();
        testProduct = Product.builder()
            .id(testProductId)
            .name("Updated Product")
            .stock(100)
            .branchId(UUID.randomUUID())
            .build();
    }

    @Test
    void execute_ShouldUpdateProductSuccessfully() {
        // Given
        when(productRepository.update(eq(testProductId), any(Product.class)))
            .thenReturn(Mono.just(testProduct));

        // When & Then
        StepVerifier.create(updateProductUseCase.execute(testProductId, testProduct))
            .expectNext(testProduct)
            .verifyComplete();

        verify(productRepository, times(1)).update(testProductId, testProduct);
    }

    @Test
    void execute_ShouldHandleRepositoryError() {
        // Given
        RuntimeException error = new RuntimeException("Update error");
        when(productRepository.update(eq(testProductId), any(Product.class)))
            .thenReturn(Mono.error(error));

        // When & Then
        StepVerifier.create(updateProductUseCase.execute(testProductId, testProduct))
            .expectError(RuntimeException.class)
            .verify();

        verify(productRepository, times(1)).update(testProductId, testProduct);
    }
}
