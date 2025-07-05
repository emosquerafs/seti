package com.seti.challenge.franchiseservice.application.usecases.product;


import com.seti.challenge.franchiseservice.domain.gateway.BranchRepository;
import com.seti.challenge.franchiseservice.domain.gateway.ProductRepository;

import com.seti.challenge.franchiseservice.domain.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetMaxStockProductPerBranchForFranchiseUseCase {

    private static final Logger log = LoggerFactory.getLogger(GetMaxStockProductPerBranchForFranchiseUseCase.class);

    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    public Flux<Product> execute(UUID franchiseId) {
        log.info("Finding max-stock product per branch for franchise {}", franchiseId);
        return branchRepository.findByFranchiseId(franchiseId)
                .flatMap(branch ->
                        productRepository.findWithMaxStockByBranch(branch.getId())
                                .filter(Objects::nonNull)
                                .map(product -> {
                                    product.setBranchId(branch.getId());
                                    return product;
                                })
                );
    }
}
