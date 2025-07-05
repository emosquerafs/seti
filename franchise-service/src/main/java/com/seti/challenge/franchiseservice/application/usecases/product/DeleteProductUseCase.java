package com.seti.challenge.franchiseservice.application.usecases.product;


import com.seti.challenge.franchiseservice.domain.gateway.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteProductUseCase {

    private static final Logger log = LoggerFactory.getLogger(DeleteProductUseCase.class);
    private final ProductRepository productRepository;

    public Mono<Void> execute(UUID productId) {
        log.info("Deleting product {}", productId);
        return productRepository.delete(productId)
                .doOnSuccess(v -> log.info("Product deleted: {}", productId))
                .doOnError(e -> log.error("Error deleting product {}: {}", productId, e.getMessage()));
    }
}
