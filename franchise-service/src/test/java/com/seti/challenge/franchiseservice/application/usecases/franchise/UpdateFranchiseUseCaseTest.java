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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateFranchiseUseCaseTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private UpdateFranchiseUseCase updateFranchiseUseCase;

    private Franchise existingFranchise;
    private Franchise updateData;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        existingFranchise = Franchise.builder()
            .id(testId)
            .name("Original Name")
            .build();
            
        updateData = Franchise.builder()
            .id(testId)
            .name("Updated Name")
            .build();
    }

    @Test
    void execute_ShouldUpdateFranchiseSuccessfully() {
        // Given
        when(franchiseRepository.findById(testId))
            .thenReturn(Mono.just(existingFranchise));
        when(franchiseRepository.update(eq(testId), any(Franchise.class)))
            .thenReturn(Mono.just(updateData));

        // When & Then
        StepVerifier.create(updateFranchiseUseCase.execute(testId, updateData))
            .expectNext(updateData)
            .verifyComplete();

        verify(franchiseRepository, times(1)).findById(testId);
        verify(franchiseRepository, times(1)).update(eq(testId), any(Franchise.class));
    }

    @Test
    void execute_ShouldReturnErrorWhenFranchiseNotFound() {
        // Given
        when(franchiseRepository.findById(testId))
            .thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(updateFranchiseUseCase.execute(testId, updateData))
            .expectErrorMatches(throwable -> throwable instanceof RuntimeException 
                && throwable.getMessage().equals("Franchise not found"))
            .verify();

        verify(franchiseRepository, times(1)).findById(testId);
        verify(franchiseRepository, never()).update(any(UUID.class), any(Franchise.class));
    }

    @Test
    void execute_ShouldHandleRepositoryError() {
        // Given
        when(franchiseRepository.findById(testId))
            .thenReturn(Mono.just(existingFranchise));
        when(franchiseRepository.update(eq(testId), any(Franchise.class)))
            .thenReturn(Mono.error(new RuntimeException("Database error")));

        // When & Then
        StepVerifier.create(updateFranchiseUseCase.execute(testId, updateData))
            .expectError(RuntimeException.class)
            .verify();

        verify(franchiseRepository, times(1)).findById(testId);
        verify(franchiseRepository, times(1)).update(eq(testId), any(Franchise.class));
    }
}
