package com.seti.challenge.franchiseservice.infrastructure.entrypoints.api;

import com.seti.challenge.franchiseservice.application.usecases.branch.*;
import com.seti.challenge.franchiseservice.domain.model.branch.Branch;
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
class BranchControllerTest {

    @Mock
    private CreateBranchUseCase createBranchUseCase;
    
    @Mock
    private UpdateBranchUseCase updateBranchUseCase;
    
    @Mock
    private DeleteBranchUseCase deleteBranchUseCase;
    
    @Mock
    private GetBranchByIdUseCase getBranchByIdUseCase;
    
    @Mock
    private GetBranchesByFranchiseUseCase getBranchesByFranchiseUseCase;

    @InjectMocks
    private BranchController branchController;

    private Branch branch;
    private UUID branchId;
    private UUID franchiseId;

    @BeforeEach
    void setUp() {
        branchId = UUID.randomUUID();
        franchiseId = UUID.randomUUID();
        branch = Branch.builder()
                .id(branchId)
                .name("Test Branch")
                .franchiseId(franchiseId)
                .build();
    }

    @Test
    void createBranch_ShouldReturnCreatedBranch() {
        // Given
        when(createBranchUseCase.execute(any(Branch.class), eq(franchiseId)))
                .thenReturn(Mono.just(branch));

        // When
        Mono<Branch> result = branchController.createBranch(franchiseId, branch);

        // Then
        StepVerifier.create(result)
                .expectNext(branch)
                .verifyComplete();
    }

    @Test
    void updateBranch_ShouldReturnUpdatedBranch() {
        // Given
        when(updateBranchUseCase.execute(eq(branchId), any(Branch.class)))
                .thenReturn(Mono.just(branch));

        // When
        Mono<Branch> result = branchController.updateBranch(branchId, branch);

        // Then
        StepVerifier.create(result)
                .expectNext(branch)
                .verifyComplete();
    }

    @Test
    void deleteBranch_ShouldReturnVoid() {
        // Given
        when(deleteBranchUseCase.execute(branchId))
                .thenReturn(Mono.empty());

        // When
        Mono<Void> result = branchController.deleteBranch(branchId);

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void getBranchById_ShouldReturnBranch() {
        // Given
        when(getBranchByIdUseCase.execute(branchId))
                .thenReturn(Mono.just(branch));

        // When
        Mono<Branch> result = branchController.getBranchById(branchId);

        // Then
        StepVerifier.create(result)
                .expectNext(branch)
                .verifyComplete();
    }

    @Test
    void getBranchesByFranchise_ShouldReturnBranches() {
        // Given
        Branch branch2 = Branch.builder()
                .id(UUID.randomUUID())
                .name("Test Branch 2")
                .franchiseId(franchiseId)
                .build();
        
        when(getBranchesByFranchiseUseCase.execute(franchiseId))
                .thenReturn(Flux.just(branch, branch2));

        // When
        Flux<Branch> result = branchController.getBranchesByFranchise(franchiseId);

        // Then
        StepVerifier.create(result)
                .expectNext(branch)
                .expectNext(branch2)
                .verifyComplete();
    }
}
