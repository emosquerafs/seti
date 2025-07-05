package com.seti.challenge.franchiseservice.application.usecases.product;

import com.seti.challenge.franchiseservice.domain.model.product.Product;
import com.seti.challenge.franchiseservice.domain.gateway.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetProductsByBranchUseCase {

    private static final Logger log = LoggerFactory.getLogger(GetProductsByBranchUseCase.class);
    private final ProductRepository productRepository;

    public Flux<Product> execute(UUID branchId) {
        log.info("Getting products for branch {}", branchId);
        return productRepository.findByBranchId(branchId)
                .doOnNext(product -> log.info("Product found: {}", product))
                .doOnError(e -> log.error("Error finding products for branch {}: {}", branchId, e.getMessage()));
    }
}
