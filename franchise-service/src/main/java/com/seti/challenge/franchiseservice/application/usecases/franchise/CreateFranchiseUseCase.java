package com.seti.challenge.franchiseservice.application.usecases.franchise;



import com.seti.challenge.franchiseservice.domain.model.franchise.Franchise;
import com.seti.challenge.franchiseservice.domain.gateway.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateFranchiseUseCase {

    private static final Logger log = LoggerFactory.getLogger(CreateFranchiseUseCase.class);

    private final FranchiseRepository franchiseRepository;

    public Mono<Franchise> execute(Franchise franchise) {
        log.info("Starting CreateFranchiseUseCase for name={}", franchise.getName());

        return franchiseRepository.save(franchise)
                .doOnNext(saved -> log.info("Franchise created: {}", saved))
                .doOnError(e -> log.error("Error in CreateFranchiseUseCase: {}", e.getMessage()))
                .doOnSuccess(f -> log.info("Finished CreateFranchiseUseCase for id={}", f.getId()));
    }
}
