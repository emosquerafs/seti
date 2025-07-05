package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.product.repository;

import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.product.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ProductR2dbcRepository extends ReactiveCrudRepository<ProductEntity, UUID> {
    Flux<ProductEntity> findByBranchId(UUID branchId);
}