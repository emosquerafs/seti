package com.seti.challenge.franchiseservice.application.usecases.branch;

import com.seti.challenge.franchiseservice.domain.gateway.BranchRepository;
import com.seti.challenge.franchiseservice.domain.model.branch.Branch;
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
class GetBranchesByFranchiseUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private GetBranchesByFranchiseUseCase getBranchesByFranchiseUseCase;

    private UUID testFranchiseId;
    private Branch branch1;
    private Branch branch2;

    @BeforeEach
    void setUp() {
        testFranchiseId = UUID.randomUUID();
        
        branch1 = Branch.builder()
            .id(UUID.randomUUID())
            .name("Branch 1")
            .franchiseId(testFranchiseId)
            .build();
            
        branch2 = Branch.builder()
            .id(UUID.randomUUID())
            .name("Branch 2")
            .franchiseId(testFranchiseId)
            .build();
    }

    @Test
    void execute_ShouldReturnBranchesForFranchise() {
        // Given
        when(branchRepository.findByFranchiseId(testFranchiseId))
            .thenReturn(Flux.just(branch1, branch2));

        // When & Then
        StepVerifier.create(getBranchesByFranchiseUseCase.execute(testFranchiseId))
            .expectNext(branch1)
            .expectNext(branch2)
            .verifyComplete();

        verify(branchRepository, times(1)).findByFranchiseId(testFranchiseId);
    }

    @Test
    void execute_ShouldReturnEmptyWhenNoBranches() {
        // Given
        when(branchRepository.findByFranchiseId(testFranchiseId))
            .thenReturn(Flux.empty());

        // When & Then
        StepVerifier.create(getBranchesByFranchiseUseCase.execute(testFranchiseId))
            .verifyComplete();

        verify(branchRepository, times(1)).findByFranchiseId(testFranchiseId);
    }

    @Test
    void execute_ShouldHandleRepositoryError() {
        // Given
        RuntimeException error = new RuntimeException("Database error");
        when(branchRepository.findByFranchiseId(testFranchiseId))
            .thenReturn(Flux.error(error));

        // When & Then
        StepVerifier.create(getBranchesByFranchiseUseCase.execute(testFranchiseId))
            .expectError(RuntimeException.class)
            .verify();

        verify(branchRepository, times(1)).findByFranchiseId(testFranchiseId);
    }
}
