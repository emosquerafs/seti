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
public class UpdateFranchiseUseCase {

    private static final Logger log = LoggerFactory.getLogger(UpdateFranchiseUseCase.class);

    private final FranchiseRepository franchiseRepository;

    public Mono<Franchise> execute(UUID id, Franchise franchise) {
        log.info("Starting UpdateFranchiseUseCase for id={}", id);

        return franchiseRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Franchise with id={} not found for update", id);
                    return Mono.error(new RuntimeException("Franchise not found"));
                }))
                .flatMap(existing -> {
                    existing.setName(franchise.getName());
                    return franchiseRepository.update(id, existing);
                })
                .doOnNext(updated -> log.info("Franchise updated: {}", updated))
                .doOnError(e -> log.error("Error updating Franchise id={}: {}", id, e.getMessage()))
                .doOnSuccess(f -> log.info("Finished UpdateFranchiseUseCase for id={}", id));
    }
}
