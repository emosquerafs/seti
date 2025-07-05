package com.seti.challenge.franchiseservice.application.usecases.branch;

import com.seti.challenge.franchiseservice.domain.gateway.BranchRepository;
import com.seti.challenge.franchiseservice.domain.model.branch.Branch;
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
class CreateBranchUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private CreateBranchUseCase createBranchUseCase;

    private Branch testBranch;
    private UUID testFranchiseId;
    private UUID testBranchId;

    @BeforeEach
    void setUp() {
        testFranchiseId = UUID.randomUUID();
        testBranchId = UUID.randomUUID();
        testBranch = Branch.builder()
            .id(testBranchId)
            .name("Test Branch")
            .franchiseId(testFranchiseId)
            .build();
    }

    @Test
    void execute_ShouldCreateBranchSuccessfully() {
        // Given
        when(branchRepository.save(any(Branch.class), eq(testFranchiseId)))
            .thenReturn(Mono.just(testBranch));

        // When & Then
        StepVerifier.create(createBranchUseCase.execute(testBranch, testFranchiseId))
            .expectNext(testBranch)
            .verifyComplete();

        verify(branchRepository, times(1)).save(testBranch, testFranchiseId);
    }

    @Test
    void execute_ShouldHandleRepositoryError() {
        // Given
        RuntimeException error = new RuntimeException("Repository error");
        when(branchRepository.save(any(Branch.class), eq(testFranchiseId)))
            .thenReturn(Mono.error(error));

        // When & Then
        StepVerifier.create(createBranchUseCase.execute(testBranch, testFranchiseId))
            .expectError(RuntimeException.class)
            .verify();

        verify(branchRepository, times(1)).save(testBranch, testFranchiseId);
    }
}
