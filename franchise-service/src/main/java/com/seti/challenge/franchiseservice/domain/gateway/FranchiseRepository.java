package com.seti.challenge.franchiseservice.domain.gateway;


import com.seti.challenge.franchiseservice.domain.model.franchise.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FranchiseRepository {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> update(UUID id, Franchise franchise);
    Mono<Void> delete(UUID id);
    Mono<Franchise> findById(UUID id);
    Flux<Franchise> findAll();
}
