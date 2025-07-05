package com.seti.challenge.franchiseservice.application.usecases.franchise;

import com.seti.challenge.franchiseservice.domain.gateway.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteFranchiseUseCase {

    private static final Logger log = LoggerFactory.getLogger(DeleteFranchiseUseCase.class);

    private final FranchiseRepository franchiseRepository;

    public Mono<Void> execute(UUID id) {
        log.info("Starting DeleteFranchiseUseCase for id={}", id);

        return franchiseRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Franchise with id={} not found for deletion", id);
                    return Mono.error(new RuntimeException("Franchise not found"));
                }))
                .flatMap(franchise -> franchiseRepository.delete(id))
                .doOnSuccess(v -> log.info("Successfully deleted Franchise id={}", id))
                .doOnError(e -> log.error("Error deleting Franchise id={}: {}", id, e.getMessage()));
    }
}