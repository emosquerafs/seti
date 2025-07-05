package com.seti.challenge.franchiseservice.application.usecases.product;

import com.seti.challenge.franchiseservice.domain.gateway.ProductRepository;
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
class DeleteProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductUseCase deleteProductUseCase;

    private UUID testProductId;

    @BeforeEach
    void setUp() {
        testProductId = UUID.randomUUID();
    }

    @Test
    void execute_ShouldDeleteProductSuccessfully() {
        // Given
        when(productRepository.delete(testProductId))
            .thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(deleteProductUseCase.execute(testProductId))
            .verifyComplete();

        verify(productRepository, times(1)).delete(testProductId);
    }

    @Test
    void execute_ShouldHandleRepositoryError() {
        // Given
        RuntimeException error = new RuntimeException("Delete error");
        when(productRepository.delete(testProductId))
            .thenReturn(Mono.error(error));

        // When & Then
        StepVerifier.create(deleteProductUseCase.execute(testProductId))
            .expectError(RuntimeException.class)
            .verify();

        verify(productRepository, times(1)).delete(testProductId);
    }
}
