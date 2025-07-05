package com.seti.challenge.franchiseservice.application.usecases.branch;


import com.seti.challenge.franchiseservice.domain.gateway.BranchRepository;
import com.seti.challenge.franchiseservice.domain.model.branch.Branch;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetBranchByIdUseCase {

    private static final Logger log = LoggerFactory.getLogger(GetBranchByIdUseCase.class);

    private final BranchRepository branchRepository;

    public Mono<Branch> execute(UUID branchId) {
        log.info("Starting GetBranchByIdUseCase for branchId={}", branchId);
        return branchRepository.findById(branchId)
                .doOnNext(branch -> log.info("Branch found: {}", branch))
                .doOnError(e -> log.error("Error finding Branch id={}: {}", branchId, e.getMessage()));
    }
}
