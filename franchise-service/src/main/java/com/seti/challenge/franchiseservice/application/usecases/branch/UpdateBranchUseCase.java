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
public class UpdateBranchUseCase {

    private static final Logger log = LoggerFactory.getLogger(UpdateBranchUseCase.class);

    private final BranchRepository branchRepository;

    public Mono<Branch> execute(UUID branchId, Branch branch) {
        log.info("Starting UpdateBranchUseCase for branchId={}", branchId);
        return branchRepository.update(branchId, branch)
                .doOnNext(updated -> log.info("Branch updated: {}", updated))
                .doOnError(e -> log.error("Error updating Branch id={}: {}", branchId, e.getMessage()));
    }
}
