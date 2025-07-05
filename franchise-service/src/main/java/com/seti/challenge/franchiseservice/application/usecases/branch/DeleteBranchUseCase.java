package com.seti.challenge.franchiseservice.application.usecases.branch;

import com.seti.challenge.franchiseservice.domain.gateway.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteBranchUseCase {

    private static final Logger log = LoggerFactory.getLogger(DeleteBranchUseCase.class);

    private final BranchRepository branchRepository;

    public Mono<Void> execute(UUID branchId) {
        log.info("Starting DeleteBranchUseCase for branchId={}", branchId);
        return branchRepository.delete(branchId)
                .doOnSuccess(v -> log.info("Branch deleted: {}", branchId))
                .doOnError(e -> log.error("Error deleting Branch id={}: {}", branchId, e.getMessage()));
    }
}
