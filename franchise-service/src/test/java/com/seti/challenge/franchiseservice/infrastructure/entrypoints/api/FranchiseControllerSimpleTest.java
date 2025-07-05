package com.seti.challenge.franchiseservice.infrastructure.entrypoints.api;

import com.seti.challenge.franchiseservice.application.usecases.franchise.*;
import com.seti.challenge.franchiseservice.domain.model.franchise.Franchise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseControllerTest {

    @Mock
    private CreateFranchiseUseCase createFranchiseUseCase;

    @Mock
    private UpdateFranchiseUseCase updateFranchiseUseCase;

    @Mock
    private DeleteFranchiseUseCase deleteFranchiseUseCase;

    @Mock
    private GetFranchiseByIdUseCase getFranchiseByIdUseCase;

    @Mock
    private GetAllFranchisesUseCase getAllFranchisesUseCase;

    @InjectMocks
    private FranchiseController franchiseController;

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
    void create_ShouldReturnCreatedFranchise() {
        // Given
        when(createFranchiseUseCase.execute(any(Franchise.class)))
            .thenReturn(Mono.just(testFranchise));

        // When & Then
        StepVerifier.create(franchiseController.create(testFranchise))
            .expectNext(testFranchise)
            .verifyComplete();
    }

    @Test
    void update_ShouldReturnUpdatedFranchise() {
        // Given
        when(updateFranchiseUseCase.execute(eq(testId), any(Franchise.class)))
            .thenReturn(Mono.just(testFranchise));

        // When & Then
        StepVerifier.create(franchiseController.update(testId, testFranchise))
            .expectNext(testFranchise)
            .verifyComplete();
    }

    @Test
    void delete_ShouldReturnVoid() {
        // Given
        when(deleteFranchiseUseCase.execute(testId))
            .thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(franchiseController.delete(testId))
            .verifyComplete();
    }

    @Test
    void getById_ShouldReturnFranchise() {
        // Given
        when(getFranchiseByIdUseCase.execute(testId))
            .thenReturn(Mono.just(testFranchise));

        // When & Then
        StepVerifier.create(franchiseController.getById(testId))
            .expectNext(testFranchise)
            .verifyComplete();
    }

    @Test
    void getAll_ShouldReturnAllFranchises() {
        // Given
        Franchise franchise2 = Franchise.builder()
            .id(UUID.randomUUID())
            .name("Another Franchise")
            .build();
        
        when(getAllFranchisesUseCase.execute())
            .thenReturn(Flux.just(testFranchise, franchise2));

        // When & Then
        StepVerifier.create(franchiseController.getAll())
            .expectNext(testFranchise)
            .expectNext(franchise2)
            .verifyComplete();
    }

    @Test
    void getById_ShouldHandleError() {
        // Given
        when(getFranchiseByIdUseCase.execute(testId))
            .thenReturn(Mono.error(new RuntimeException("Franchise not found")));

        // When & Then
        StepVerifier.create(franchiseController.getById(testId))
            .expectError(RuntimeException.class)
            .verify();
    }
}
