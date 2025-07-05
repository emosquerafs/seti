package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.branch.repository;


import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.branch.entity.BranchEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import java.util.UUID;

@Repository
public interface BranchR2dbcRepository extends ReactiveCrudRepository<BranchEntity, UUID> {
    Flux<BranchEntity> findByFranchiseId(UUID franchiseId);
}