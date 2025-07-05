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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductUseCase createProductUseCase;

    private Product testProduct;
    private UUID testBranchId;

    @BeforeEach
    void setUp() {
        testBranchId = UUID.randomUUID();
        testProduct = Product.builder()
            .id(UUID.randomUUID())
            .name("Test Product")
            .stock(50)
            .branchId(testBranchId)
            .build();
    }

    @Test
    void execute_ShouldCreateProductSuccessfully() {
        // Given
        when(productRepository.save(any(Product.class), eq(testBranchId)))
            .thenReturn(Mono.just(testProduct));

        // When & Then
        StepVerifier.create(createProductUseCase.execute(testProduct, testBranchId))
            .expectNext(testProduct)
            .verifyComplete();

        verify(productRepository, times(1)).save(testProduct, testBranchId);
    }

    @Test
    void execute_ShouldHandleRepositoryError() {
        // Given
        RuntimeException error = new RuntimeException("Repository error");
        when(productRepository.save(any(Product.class), eq(testBranchId)))
            .thenReturn(Mono.error(error));

        // When & Then
        StepVerifier.create(createProductUseCase.execute(testProduct, testBranchId))
            .expectError(RuntimeException.class)
            .verify();

        verify(productRepository, times(1)).save(testProduct, testBranchId);
    }
}
