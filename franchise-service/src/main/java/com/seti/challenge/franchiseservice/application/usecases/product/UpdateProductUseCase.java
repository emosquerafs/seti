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
public class UpdateProductUseCase {

    private static final Logger log = LoggerFactory.getLogger(UpdateProductUseCase.class);
    private final ProductRepository productRepository;

    public Mono<Product> execute(UUID productId, Product product) {
        log.info("Updating product {}: {}", productId, product.getName());
        return productRepository.update(productId, product)
                .doOnNext(updated -> log.info("Product updated: {}", updated))
                .doOnError(e -> log.error("Error updating product {}: {}", productId, e.getMessage()));
    }
}
