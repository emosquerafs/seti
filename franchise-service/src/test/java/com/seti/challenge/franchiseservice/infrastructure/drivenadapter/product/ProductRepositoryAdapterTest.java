package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.product;

import com.seti.challenge.franchiseservice.domain.model.product.Product;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.product.entity.ProductEntity;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.product.repository.ProductR2dbcRepository;
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
class ProductRepositoryAdapterTest {

    @Mock
    private ProductR2dbcRepository repository;

    @InjectMocks
    private ProductRepositoryAdapter productRepositoryAdapter;

    private Product product;
    private ProductEntity productEntity;
    private UUID productId;
    private UUID branchId;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        branchId = UUID.randomUUID();
        
        product = Product.builder()
                .id(productId)
                .name("Test Product")
                .stock(10)
                .branchId(branchId)
                .build();
        
        productEntity = ProductEntity.builder()
                .id(productId)
                .name("Test Product")
                .stock(10)
                .branchId(branchId)
                .build();
    }

    @Test
    void save_ShouldReturnSavedProduct() {
        // Given
        when(repository.save(any(ProductEntity.class)))
                .thenReturn(Mono.just(productEntity));

        // When
        Mono<Product> result = productRepositoryAdapter.save(product, branchId);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(savedProduct -> 
                    savedProduct.getId().equals(productId) &&
                    savedProduct.getName().equals("Test Product") &&
                    savedProduct.getStock() == 10 &&
                    savedProduct.getBranchId().equals(branchId))
                .verifyComplete();
    }

    @Test
    void update_ShouldReturnUpdatedProduct() {
        // Given
        ProductEntity existingEntity = ProductEntity.builder()
                .id(productId)
                .name("Old Name")
                .stock(5)
                .branchId(branchId)
                .build();
        
        ProductEntity updatedEntity = ProductEntity.builder()
                .id(productId)
                .name("Test Product")
                .stock(10)
                .branchId(branchId)
                .build();

        when(repository.findById(productId))
                .thenReturn(Mono.just(existingEntity));
        when(repository.save(any(ProductEntity.class)))
                .thenReturn(Mono.just(updatedEntity));

        // When
        Mono<Product> result = productRepositoryAdapter.update(productId, product);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(updatedProduct -> 
                    updatedProduct.getId().equals(productId) &&
                    updatedProduct.getName().equals("Test Product") &&
                    updatedProduct.getStock() == 10)
                .verifyComplete();
    }

    @Test
    void update_ShouldReturnEmptyWhenNotFound() {
        // Given
        when(repository.findById(productId))
                .thenReturn(Mono.empty());

        // When
        Mono<Product> result = productRepositoryAdapter.update(productId, product);

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void delete_ShouldCompleteSuccessfully() {
        // Given
        when(repository.deleteById(productId))
                .thenReturn(Mono.empty());

        // When
        Mono<Void> result = productRepositoryAdapter.delete(productId);

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void findById_ShouldReturnProduct() {
        // Given
        when(repository.findById(productId))
                .thenReturn(Mono.just(productEntity));

        // When
        Mono<Product> result = productRepositoryAdapter.findById(productId);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(foundProduct -> 
                    foundProduct.getId().equals(productId) &&
                    foundProduct.getName().equals("Test Product"))
                .verifyComplete();
    }

    @Test
    void findById_ShouldReturnEmptyWhenNotFound() {
        // Given
        when(repository.findById(productId))
                .thenReturn(Mono.empty());

        // When
        Mono<Product> result = productRepositoryAdapter.findById(productId);

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void findByBranchId_ShouldReturnProducts() {
        // Given
        ProductEntity entity2 = ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Test Product 2")
                .stock(15)
                .branchId(branchId)
                .build();
        
        when(repository.findByBranchId(branchId))
                .thenReturn(Flux.just(productEntity, entity2));

        // When
        Flux<Product> result = productRepositoryAdapter.findByBranchId(branchId);

        // Then
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void findByBranchId_ShouldReturnEmptyWhenNoProducts() {
        // Given
        when(repository.findByBranchId(branchId))
                .thenReturn(Flux.empty());

        // When
        Flux<Product> result = productRepositoryAdapter.findByBranchId(branchId);

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void findWithMaxStockByBranch_ShouldReturnProductWithMaxStock() {
        // Given
        ProductEntity entity1 = ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Product 1")
                .stock(10)
                .branchId(branchId)
                .build();
        
        ProductEntity entity2 = ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Product 2")
                .stock(25)
                .branchId(branchId)
                .build();
        
        ProductEntity entity3 = ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Product 3")
                .stock(15)
                .branchId(branchId)
                .build();
        
        when(repository.findByBranchId(branchId))
                .thenReturn(Flux.just(entity1, entity2, entity3));

        // When
        Mono<Product> result = productRepositoryAdapter.findWithMaxStockByBranch(branchId);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(product -> 
                    product.getName().equals("Product 2") &&
                    product.getStock() == 25)
                .verifyComplete();
    }

    @Test
    void findWithMaxStockByBranch_ShouldReturnEmptyWhenNoProducts() {
        // Given
        when(repository.findByBranchId(branchId))
                .thenReturn(Flux.empty());

        // When
        Mono<Product> result = productRepositoryAdapter.findWithMaxStockByBranch(branchId);

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }
}
