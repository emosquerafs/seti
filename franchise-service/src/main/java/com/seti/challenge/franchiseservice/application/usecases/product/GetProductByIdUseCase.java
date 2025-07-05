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
public class GetProductByIdUseCase {

    private static final Logger log = LoggerFactory.getLogger(GetProductByIdUseCase.class);
    private final ProductRepository productRepository;

    public Mono<Product> execute(UUID productId) {
        log.info("Getting product by id {}", productId);
        return productRepository.findById(productId)
                .doOnNext(product -> log.info("Product found: {}", product))
                .doOnError(e -> log.error("Error finding product {}: {}", productId, e.getMessage()));
    }
}
