package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.franchise;



import com.seti.challenge.franchiseservice.domain.gateway.FranchiseRepository;
import com.seti.challenge.franchiseservice.domain.model.franchise.Franchise;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.franchise.entity.FranchiseEntity;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.franchise.mapper.FranchiseMapper;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.franchise.repository.FranchiseR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FranchiseRepositoryAdapter implements FranchiseRepository {

    private final FranchiseR2dbcRepository repository;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        FranchiseEntity entity = FranchiseMapper.toEntity(franchise);
        return repository.save(entity)
                .map(FranchiseMapper::toDomain);
    }

    @Override
    public Mono<Franchise> update(UUID id, Franchise franchise) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setName(franchise.getName());
                    return repository.save(existing);
                })
                .map(FranchiseMapper::toDomain);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Franchise> findById(UUID id) {
        return repository.findById(id)
                .map(FranchiseMapper::toDomain);
    }

    @Override
    public Flux<Franchise> findAll() {
        return repository.findAll()
                .map(FranchiseMapper::toDomain);
    }
}
