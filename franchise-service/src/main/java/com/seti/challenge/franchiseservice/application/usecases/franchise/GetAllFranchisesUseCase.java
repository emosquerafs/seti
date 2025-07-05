package com.seti.challenge.franchiseservice.application.usecases.franchise;

import com.seti.challenge.franchiseservice.domain.gateway.FranchiseRepository;
import com.seti.challenge.franchiseservice.domain.model.franchise.Franchise;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GetAllFranchisesUseCase {

    private static final Logger log = LoggerFactory.getLogger(GetAllFranchisesUseCase.class);

    private final FranchiseRepository franchiseRepository;

    public Flux<Franchise> execute() {
        log.info("Starting GetAllFranchisesUseCase");

        return franchiseRepository.findAll()
                .doOnNext(franchise -> log.info("Franchise found: {}", franchise))
                .doOnError(e -> log.error("Error getting all Franchises: {}", e.getMessage()))
                .doOnComplete(() -> log.info("Finished GetAllFranchisesUseCase"));
    }
}
