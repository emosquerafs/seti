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
class UpdateBranchUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private UpdateBranchUseCase updateBranchUseCase;

    private Branch testBranch;
    private UUID testBranchId;

    @BeforeEach
    void setUp() {
        testBranchId = UUID.randomUUID();
        testBranch = Branch.builder()
            .id(testBranchId)
            .name("Updated Branch")
            .franchiseId(UUID.randomUUID())
            .build();
    }

    @Test
    void execute_ShouldUpdateBranchSuccessfully() {
        // Given
        when(branchRepository.update(eq(testBranchId), any(Branch.class)))
            .thenReturn(Mono.just(testBranch));

        // When & Then
        StepVerifier.create(updateBranchUseCase.execute(testBranchId, testBranch))
            .expectNext(testBranch)
            .verifyComplete();

        verify(branchRepository, times(1)).update(testBranchId, testBranch);
    }

    @Test
    void execute_ShouldHandleRepositoryError() {
        // Given
        RuntimeException error = new RuntimeException("Update error");
        when(branchRepository.update(eq(testBranchId), any(Branch.class)))
            .thenReturn(Mono.error(error));

        // When & Then
        StepVerifier.create(updateBranchUseCase.execute(testBranchId, testBranch))
            .expectError(RuntimeException.class)
            .verify();

        verify(branchRepository, times(1)).update(testBranchId, testBranch);
    }
}
