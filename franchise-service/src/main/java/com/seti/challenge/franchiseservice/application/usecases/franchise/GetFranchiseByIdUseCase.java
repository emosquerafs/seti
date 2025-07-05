package com.seti.challenge.franchiseservice.application.usecases.franchise;


import com.seti.challenge.franchiseservice.domain.gateway.FranchiseRepository;
import com.seti.challenge.franchiseservice.domain.model.franchise.Franchise;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class GetFranchiseByIdUseCase {

    private static final Logger log = LoggerFactory.getLogger(GetFranchiseByIdUseCase.class);

    private final FranchiseRepository franchiseRepository;

    public Mono<Franchise> execute(UUID id) {
        log.info("Starting GetFranchiseByIdUseCase for id={}", id);

        return franchiseRepository.findById(id)
                .doOnNext(franchise -> log.info("Franchise found: {}", franchise))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("No Franchise found for id={}", id);
                    return Mono.error(new RuntimeException("Franchise not found"));
                }))
                .doOnError(e -> log.error("Error in GetFranchiseByIdUseCase for id={}: {}", id, e.getMessage()))
                .doOnSuccess(f -> log.info("Finished GetFranchiseByIdUseCase for id={}", id));
    }
}