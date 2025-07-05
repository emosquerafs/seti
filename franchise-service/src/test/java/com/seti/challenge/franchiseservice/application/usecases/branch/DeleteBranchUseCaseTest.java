package com.seti.challenge.franchiseservice.application.usecases.branch;

import com.seti.challenge.franchiseservice.domain.gateway.BranchRepository;
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
class DeleteBranchUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private DeleteBranchUseCase deleteBranchUseCase;

    private UUID testBranchId;

    @BeforeEach
    void setUp() {
        testBranchId = UUID.randomUUID();
    }

    @Test
    void execute_ShouldDeleteBranchSuccessfully() {
        // Given
        when(branchRepository.delete(testBranchId))
            .thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(deleteBranchUseCase.execute(testBranchId))
            .verifyComplete();

        verify(branchRepository, times(1)).delete(testBranchId);
    }

    @Test
    void execute_ShouldHandleRepositoryError() {
        // Given
        RuntimeException error = new RuntimeException("Delete error");
        when(branchRepository.delete(testBranchId))
            .thenReturn(Mono.error(error));

        // When & Then
        StepVerifier.create(deleteBranchUseCase.execute(testBranchId))
            .expectError(RuntimeException.class)
            .verify();

        verify(branchRepository, times(1)).delete(testBranchId);
    }
}
