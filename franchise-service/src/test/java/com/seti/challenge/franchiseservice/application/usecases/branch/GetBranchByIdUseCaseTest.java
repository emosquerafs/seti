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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetBranchByIdUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private GetBranchByIdUseCase getBranchByIdUseCase;

    private Branch testBranch;
    private UUID testBranchId;
    private UUID testFranchiseId;

    @BeforeEach
    void setUp() {
        testBranchId = UUID.randomUUID();
        testFranchiseId = UUID.randomUUID();
        testBranch = Branch.builder()
            .id(testBranchId)
            .name("Test Branch")
            .franchiseId(testFranchiseId)
            .build();
    }

    @Test
    void execute_ShouldReturnBranchWhenFound() {
        // Given
        when(branchRepository.findById(testBranchId))
            .thenReturn(Mono.just(testBranch));

        // When & Then
        StepVerifier.create(getBranchByIdUseCase.execute(testBranchId))
            .expectNext(testBranch)
            .verifyComplete();

        verify(branchRepository, times(1)).findById(testBranchId);
    }

    @Test
    void execute_ShouldReturnEmptyWhenBranchNotFound() {
        // Given
        when(branchRepository.findById(testBranchId))
            .thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(getBranchByIdUseCase.execute(testBranchId))
            .verifyComplete();

        verify(branchRepository, times(1)).findById(testBranchId);
    }

    @Test
    void execute_ShouldHandleRepositoryError() {
        // Given
        RuntimeException error = new RuntimeException("Database error");
        when(branchRepository.findById(testBranchId))
            .thenReturn(Mono.error(error));

        // When & Then
        StepVerifier.create(getBranchByIdUseCase.execute(testBranchId))
            .expectError(RuntimeException.class)
            .verify();

        verify(branchRepository, times(1)).findById(testBranchId);
    }
}
