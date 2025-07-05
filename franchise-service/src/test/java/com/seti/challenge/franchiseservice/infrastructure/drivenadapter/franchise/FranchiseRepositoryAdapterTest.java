package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.franchise;

import com.seti.challenge.franchiseservice.domain.model.franchise.Franchise;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.franchise.entity.FranchiseEntity;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.franchise.repository.FranchiseR2dbcRepository;
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
class FranchiseRepositoryAdapterTest {

    @Mock
    private FranchiseR2dbcRepository repository;

    @InjectMocks
    private FranchiseRepositoryAdapter franchiseRepositoryAdapter;

    private Franchise franchise;
    private FranchiseEntity franchiseEntity;
    private UUID franchiseId;

    @BeforeEach
    void setUp() {
        franchiseId = UUID.randomUUID();
        franchise = Franchise.builder()
                .id(franchiseId)
                .name("Test Franchise")
                .build();
        
        franchiseEntity = FranchiseEntity.builder()
                .id(franchiseId)
                .name("Test Franchise")
                .build();
    }

    @Test
    void save_ShouldReturnSavedFranchise() {
        // Given
        when(repository.save(any(FranchiseEntity.class)))
                .thenReturn(Mono.just(franchiseEntity));

        // When
        Mono<Franchise> result = franchiseRepositoryAdapter.save(franchise);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(savedFranchise -> 
                    savedFranchise.getId().equals(franchiseId) &&
                    savedFranchise.getName().equals("Test Franchise"))
                .verifyComplete();
    }

    @Test
    void update_ShouldReturnUpdatedFranchise() {
        // Given
        FranchiseEntity existingEntity = FranchiseEntity.builder()
                .id(franchiseId)
                .name("Old Name")
                .build();
        
        FranchiseEntity updatedEntity = FranchiseEntity.builder()
                .id(franchiseId)
                .name("Test Franchise")
                .build();

        when(repository.findById(franchiseId))
                .thenReturn(Mono.just(existingEntity));
        when(repository.save(any(FranchiseEntity.class)))
                .thenReturn(Mono.just(updatedEntity));

        // When
        Mono<Franchise> result = franchiseRepositoryAdapter.update(franchiseId, franchise);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(updatedFranchise -> 
                    updatedFranchise.getId().equals(franchiseId) &&
                    updatedFranchise.getName().equals("Test Franchise"))
                .verifyComplete();
    }

    @Test
    void update_ShouldReturnEmptyWhenNotFound() {
        // Given
        when(repository.findById(franchiseId))
                .thenReturn(Mono.empty());

        // When
        Mono<Franchise> result = franchiseRepositoryAdapter.update(franchiseId, franchise);

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void delete_ShouldCompleteSuccessfully() {
        // Given
        when(repository.deleteById(franchiseId))
                .thenReturn(Mono.empty());

        // When
        Mono<Void> result = franchiseRepositoryAdapter.delete(franchiseId);

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void findById_ShouldReturnFranchise() {
        // Given
        when(repository.findById(franchiseId))
                .thenReturn(Mono.just(franchiseEntity));

        // When
        Mono<Franchise> result = franchiseRepositoryAdapter.findById(franchiseId);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(foundFranchise -> 
                    foundFranchise.getId().equals(franchiseId) &&
                    foundFranchise.getName().equals("Test Franchise"))
                .verifyComplete();
    }

    @Test
    void findById_ShouldReturnEmptyWhenNotFound() {
        // Given
        when(repository.findById(franchiseId))
                .thenReturn(Mono.empty());

        // When
        Mono<Franchise> result = franchiseRepositoryAdapter.findById(franchiseId);

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void findAll_ShouldReturnAllFranchises() {
        // Given
        FranchiseEntity entity2 = FranchiseEntity.builder()
                .id(UUID.randomUUID())
                .name("Test Franchise 2")
                .build();
        
        when(repository.findAll())
                .thenReturn(Flux.just(franchiseEntity, entity2));

        // When
        Flux<Franchise> result = franchiseRepositoryAdapter.findAll();

        // Then
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void findAll_ShouldReturnEmptyWhenNoFranchises() {
        // Given
        when(repository.findAll())
                .thenReturn(Flux.empty());

        // When
        Flux<Franchise> result = franchiseRepositoryAdapter.findAll();

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }
}
