package com.seti.challenge.franchiseservice.application.usecases.franchise;

import com.seti.challenge.franchiseservice.domain.gateway.FranchiseRepository;
import com.seti.challenge.franchiseservice.domain.model.franchise.Franchise;
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
class GetAllFranchisesUseCaseTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private GetAllFranchisesUseCase getAllFranchisesUseCase;

    private Franchise franchise1;
    private Franchise franchise2;

    @BeforeEach
    void setUp() {
        franchise1 = Franchise.builder()
            .id(UUID.randomUUID())
            .name("Franchise 1")
            .build();
            
        franchise2 = Franchise.builder()
            .id(UUID.randomUUID())
            .name("Franchise 2")
            .build();
    }

    @Test
    void execute_ShouldReturnAllFranchises() {
        // Given
        when(franchiseRepository.findAll())
            .thenReturn(Flux.just(franchise1, franchise2));

        // When & Then
        StepVerifier.create(getAllFranchisesUseCase.execute())
            .expectNext(franchise1)
            .expectNext(franchise2)
            .verifyComplete();

        verify(franchiseRepository, times(1)).findAll();
    }

    @Test
    void execute_ShouldReturnEmptyWhenNoFranchises() {
        // Given
        when(franchiseRepository.findAll())
            .thenReturn(Flux.empty());

        // When & Then
        StepVerifier.create(getAllFranchisesUseCase.execute())
            .verifyComplete();

        verify(franchiseRepository, times(1)).findAll();
    }

    @Test
    void execute_ShouldHandleRepositoryError() {
        // Given
        RuntimeException error = new RuntimeException("Database error");
        when(franchiseRepository.findAll())
            .thenReturn(Flux.error(error));

        // When & Then
        StepVerifier.create(getAllFranchisesUseCase.execute())
            .expectError(RuntimeException.class)
            .verify();

        verify(franchiseRepository, times(1)).findAll();
    }
}
