package com.seti.challenge.franchiseservice.application.usecases.branch;


import com.seti.challenge.franchiseservice.domain.gateway.BranchRepository;
import com.seti.challenge.franchiseservice.domain.model.branch.Branch;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetBranchesByFranchiseUseCase {

    private static final Logger log = LoggerFactory.getLogger(GetBranchesByFranchiseUseCase.class);

    private final BranchRepository branchRepository;

    public Flux<Branch> execute(UUID franchiseId) {
        log.info("Starting GetBranchesByFranchiseUseCase for franchiseId={}", franchiseId);
        return branchRepository.findByFranchiseId(franchiseId)
                .doOnNext(branch -> log.info("Branch found: {}", branch))
                .doOnError(e -> log.error("Error finding Branches for franchiseId={}: {}", franchiseId, e.getMessage()));
    }
}
