package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.branch;

import com.seti.challenge.franchiseservice.domain.gateway.BranchRepository;
import com.seti.challenge.franchiseservice.domain.model.branch.Branch;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.branch.entity.BranchEntity;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.branch.mapper.BranchMapper;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.branch.repository.BranchR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BranchRepositoryAdapter  implements BranchRepository {

    private final BranchR2dbcRepository repository;

    @Override
    public Mono<Branch> save(Branch branch, UUID franchiseId) {
        BranchEntity entity = BranchMapper.toEntity(branch);
        entity.setFranchiseId(franchiseId);
        return repository.save(entity).map(BranchMapper::toDomain);
    }

    @Override
    public Mono<Branch> update(UUID id, Branch branch) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setName(branch.getName());
                    return repository.save(existing);
                })
                .map(BranchMapper::toDomain);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Branch> findById(UUID id) {
        return repository.findById(id).map(BranchMapper::toDomain);
    }

    @Override
    public Flux<Branch> findByFranchiseId(UUID franchiseId) {
        return repository.findByFranchiseId(franchiseId).map(BranchMapper::toDomain);
    }
}