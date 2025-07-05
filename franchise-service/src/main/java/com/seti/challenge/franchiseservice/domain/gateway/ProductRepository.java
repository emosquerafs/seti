package com.seti.challenge.franchiseservice.domain.gateway;

import com.seti.challenge.franchiseservice.domain.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductRepository {
    Mono<Product> save(Product product, UUID branchId);
    Mono<Product> update(UUID id, Product product);
    Mono<Void> delete(UUID id);
    Mono<Product> findById(UUID id);
    Flux<Product> findByBranchId(UUID branchId);
    Mono<Product> findWithMaxStockByBranch(UUID branchId);
}
