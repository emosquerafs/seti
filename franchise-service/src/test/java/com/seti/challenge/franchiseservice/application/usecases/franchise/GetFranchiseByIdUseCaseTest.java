package com.seti.challenge.franchiseservice.application.usecases.franchise;

import com.seti.challenge.franchiseservice.domain.gateway.FranchiseRepository;
import com.seti.challenge.franchiseservice.domain.model.franchise.Franchise;
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
class GetFranchiseByIdUseCaseTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private GetFranchiseByIdUseCase getFranchiseByIdUseCase;

    private Franchise testFranchise;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testFranchise = Franchise.builder()
            .id(testId)
            .name("Test Franchise")
            .build();
    }

    @Test
    void execute_ShouldReturnFranchiseWhenFound() {
        // Given
        when(franchiseRepository.findById(testId))
            .thenReturn(Mono.just(testFranchise));

        // When & Then
        StepVerifier.create(getFranchiseByIdUseCase.execute(testId))
            .expectNext(testFranchise)
            .verifyComplete();

        verify(franchiseRepository, times(1)).findById(testId);
    }

    @Test
    void execute_ShouldReturnErrorWhenFranchiseNotFound() {
        // Given
        when(franchiseRepository.findById(testId))
            .thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(getFranchiseByIdUseCase.execute(testId))
            .expectErrorMatches(throwable -> throwable instanceof RuntimeException 
                && throwable.getMessage().equals("Franchise not found"))
            .verify();

        verify(franchiseRepository, times(1)).findById(testId);
    }

    @Test
    void execute_ShouldHandleRepositoryError() {
        // Given
        RuntimeException error = new RuntimeException("Database error");
        when(franchiseRepository.findById(testId))
            .thenReturn(Mono.error(error));

        // When & Then
        StepVerifier.create(getFranchiseByIdUseCase.execute(testId))
            .expectError(RuntimeException.class)
            .verify();

        verify(franchiseRepository, times(1)).findById(testId);
    }
}
