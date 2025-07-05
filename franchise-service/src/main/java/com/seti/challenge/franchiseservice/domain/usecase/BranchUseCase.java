package com.seti.challenge.franchiseservice.domain.usecase;



import com.seti.challenge.franchiseservice.domain.model.branch.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BranchUseCase {
    Mono<Branch> create(Branch branch, UUID franchiseId);
    Mono<Branch> update(UUID id, Branch branch);
    Mono<Void> delete(UUID id);
    Mono<Branch> findById(UUID id);
    Flux<Branch> findByFranchiseId(UUID franchiseId);
}
