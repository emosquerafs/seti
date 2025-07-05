package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.product;

import com.seti.challenge.franchiseservice.domain.gateway.ProductRepository;
import com.seti.challenge.franchiseservice.domain.model.product.Product;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.product.entity.ProductEntity;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.product.mapper.ProductMapper;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.product.repository.ProductR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductR2dbcRepository repository;

    @Override
    public Mono<Product> save(Product product, UUID branchId) {
        ProductEntity entity = ProductMapper.toEntity(product);
        entity.setBranchId(branchId);
        return repository.save(entity).map(ProductMapper::toDomain);
    }

    @Override
    public Mono<Product> update(UUID id, Product product) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setName(product.getName());
                    existing.setStock(product.getStock());
                    return repository.save(existing);
                })
                .map(ProductMapper::toDomain);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Product> findById(UUID id) {
        return repository.findById(id).map(ProductMapper::toDomain);
    }

    @Override
    public Flux<Product> findByBranchId(UUID branchId) {
        return repository.findByBranchId(branchId).map(ProductMapper::toDomain);
    }

    @Override
    public Mono<Product> findWithMaxStockByBranch(UUID branchId) {
        return repository.findByBranchId(branchId)
                .sort((a, b) -> Integer.compare(b.getStock(), a.getStock())) // Ordena descendente por stock
                .next() // Toma el primero (mayor stock)
                .map(ProductMapper::toDomain);
    }

}