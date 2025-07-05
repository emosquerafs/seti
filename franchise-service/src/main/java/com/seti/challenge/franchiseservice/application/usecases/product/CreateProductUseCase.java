package com.seti.challenge.franchiseservice.application.usecases.product;

import com.seti.challenge.franchiseservice.domain.model.product.Product;
import com.seti.challenge.franchiseservice.domain.gateway.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateProductUseCase {

    private static final Logger log = LoggerFactory.getLogger(CreateProductUseCase.class);
    private final ProductRepository productRepository;

    public Mono<Product> execute(Product product, UUID branchId) {
        log.info("Creating product {} in branch {}", product.getName(), branchId);
        return productRepository.save(product, branchId)
                .doOnNext(saved -> log.info("Product created: {}", saved))
                .doOnError(e -> log.error("Error creating product: {}", e.getMessage()));
    }
}
