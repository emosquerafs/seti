package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.branch;

import com.seti.challenge.franchiseservice.domain.model.branch.Branch;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.branch.entity.BranchEntity;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.branch.repository.BranchR2dbcRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchRepositoryAdapterTest {

    @Mock
    private BranchR2dbcRepository repository;

    @InjectMocks
    private BranchRepositoryAdapter branchRepositoryAdapter;

    private Branch branch;
    private BranchEntity branchEntity;
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
        
        branchEntity = BranchEntity.builder()
                .id(branchId)
                .name("Test Branch")
                .franchiseId(franchiseId)
                .build();
    }

    @Test
    void save_ShouldReturnSavedBranch() {
        // Given
        when(repository.save(any(BranchEntity.class)))
                .thenReturn(Mono.just(branchEntity));

        // When
        Mono<Branch> result = branchRepositoryAdapter.save(branch, franchiseId);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(savedBranch -> 
                    savedBranch.getId().equals(branchId) &&
                    savedBranch.getName().equals("Test Branch") &&
                    savedBranch.getFranchiseId().equals(franchiseId))
                .verifyComplete();
    }

    @Test
    void update_ShouldReturnUpdatedBranch() {
        // Given
        BranchEntity existingEntity = BranchEntity.builder()
                .id(branchId)
                .name("Old Name")
                .franchiseId(franchiseId)
                .build();
        
        BranchEntity updatedEntity = BranchEntity.builder()
                .id(branchId)
                .name("Test Branch")
                .franchiseId(franchiseId)
                .build();

        when(repository.findById(branchId))
                .thenReturn(Mono.just(existingEntity));
        when(repository.save(any(BranchEntity.class)))
                .thenReturn(Mono.just(updatedEntity));

        // When
        Mono<Branch> result = branchRepositoryAdapter.update(branchId, branch);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(updatedBranch -> 
                    updatedBranch.getId().equals(branchId) &&
                    updatedBranch.getName().equals("Test Branch"))
                .verifyComplete();
    }

    @Test
    void update_ShouldReturnEmptyWhenNotFound() {
        // Given
        when(repository.findById(branchId))
                .thenReturn(Mono.empty());

        // When
        Mono<Branch> result = branchRepositoryAdapter.update(branchId, branch);

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void delete_ShouldCompleteSuccessfully() {
        // Given
        when(repository.deleteById(branchId))
                .thenReturn(Mono.empty());

        // When
        Mono<Void> result = branchRepositoryAdapter.delete(branchId);

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void findById_ShouldReturnBranch() {
        // Given
        when(repository.findById(branchId))
                .thenReturn(Mono.just(branchEntity));

        // When
        Mono<Branch> result = branchRepositoryAdapter.findById(branchId);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(foundBranch -> 
                    foundBranch.getId().equals(branchId) &&
                    foundBranch.getName().equals("Test Branch"))
                .verifyComplete();
    }

    @Test
    void findById_ShouldReturnEmptyWhenNotFound() {
        // Given
        when(repository.findById(branchId))
                .thenReturn(Mono.empty());

        // When
        Mono<Branch> result = branchRepositoryAdapter.findById(branchId);

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void findByFranchiseId_ShouldReturnBranches() {
        // Given
        BranchEntity entity2 = BranchEntity.builder()
                .id(UUID.randomUUID())
                .name("Test Branch 2")
                .franchiseId(franchiseId)
                .build();
        
        when(repository.findByFranchiseId(franchiseId))
                .thenReturn(Flux.just(branchEntity, entity2));

        // When
        Flux<Branch> result = branchRepositoryAdapter.findByFranchiseId(franchiseId);

        // Then
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void findByFranchiseId_ShouldReturnEmptyWhenNoBranches() {
        // Given
        when(repository.findByFranchiseId(franchiseId))
                .thenReturn(Flux.empty());

        // When
        Flux<Branch> result = branchRepositoryAdapter.findByFranchiseId(franchiseId);

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }
}
