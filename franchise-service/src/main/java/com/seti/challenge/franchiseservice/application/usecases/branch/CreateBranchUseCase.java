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
public class CreateBranchUseCase {

    private static final Logger log = LoggerFactory.getLogger(CreateBranchUseCase.class);

    private final BranchRepository branchRepository;

    public Mono<Branch> execute(Branch branch, UUID franchiseId) {
        log.info("Starting CreateBranchUseCase for branch={} under franchise={}", branch.getName(), franchiseId);
        return branchRepository.save(branch, franchiseId)
                .doOnNext(saved -> log.info("Branch created: {}", saved))
                .doOnError(e -> log.error("Error in CreateBranchUseCase: {}", e.getMessage()));
    }
}
