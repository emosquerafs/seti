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
public class GetProductWithMaxStockByBranchUseCase {

    private static final Logger log = LoggerFactory.getLogger(GetProductWithMaxStockByBranchUseCase.class);
    private final ProductRepository productRepository;

    public Mono<Product> execute(UUID branchId) {
        log.info("Finding product with max stock for branch {}", branchId);
        return productRepository.findByBranchId(branchId)
                .sort((a, b) -> Integer.compare(b.getStock(), a.getStock())) // Descendente
                .next() // Solo el primero (mayor stock)
                .doOnNext(product -> log.info("Product with max stock: {}", product))
                .doOnError(e -> log.error("Error finding product with max stock for branch {}: {}", branchId, e.getMessage()));
    }
}
