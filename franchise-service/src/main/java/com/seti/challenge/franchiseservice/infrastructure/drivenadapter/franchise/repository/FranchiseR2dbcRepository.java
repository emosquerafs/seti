package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.franchise.repository;

import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.franchise.entity.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FranchiseR2dbcRepository extends ReactiveCrudRepository<FranchiseEntity, UUID> {
}
