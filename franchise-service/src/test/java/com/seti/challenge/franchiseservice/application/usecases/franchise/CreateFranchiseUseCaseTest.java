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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateFranchiseUseCaseTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private CreateFranchiseUseCase createFranchiseUseCase;

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
    void execute_ShouldCreateFranchiseSuccessfully() {
        // Given
        when(franchiseRepository.save(any(Franchise.class)))
            .thenReturn(Mono.just(testFranchise));

        // When & Then
        StepVerifier.create(createFranchiseUseCase.execute(testFranchise))
            .expectNext(testFranchise)
            .verifyComplete();

        verify(franchiseRepository, times(1)).save(testFranchise);
    }

    @Test
    void execute_ShouldHandleRepositoryError() {
        // Given
        RuntimeException error = new RuntimeException("Repository error");
        when(franchiseRepository.save(any(Franchise.class)))
            .thenReturn(Mono.error(error));

        // When & Then
        StepVerifier.create(createFranchiseUseCase.execute(testFranchise))
            .expectError(RuntimeException.class)
            .verify();

        verify(franchiseRepository, times(1)).save(testFranchise);
    }

    @Test
    void execute_ShouldLogCorrectly() {
        // Given
        when(franchiseRepository.save(any(Franchise.class)))
            .thenReturn(Mono.just(testFranchise));

        // When & Then
        StepVerifier.create(createFranchiseUseCase.execute(testFranchise))
            .expectNext(testFranchise)
            .verifyComplete();

        verify(franchiseRepository, times(1)).save(testFranchise);
    }
}
