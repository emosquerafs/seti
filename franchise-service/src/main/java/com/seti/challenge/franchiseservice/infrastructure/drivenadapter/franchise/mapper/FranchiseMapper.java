package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.franchise.mapper;


import com.seti.challenge.franchiseservice.domain.model.franchise.Franchise;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.franchise.entity.FranchiseEntity;

public class FranchiseMapper {
    public static Franchise toDomain(FranchiseEntity entity) {
        return Franchise.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static FranchiseEntity toEntity(Franchise domain) {
        return FranchiseEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }
}
