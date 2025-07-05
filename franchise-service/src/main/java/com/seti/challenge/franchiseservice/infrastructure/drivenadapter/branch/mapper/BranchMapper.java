package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.branch.mapper;

import com.seti.challenge.franchiseservice.domain.model.branch.Branch;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.branch.entity.BranchEntity;

public class BranchMapper {
    private BranchMapper() {}

    public static Branch toDomain(BranchEntity entity) {
        return Branch.builder()
                .id(entity.getId())
                .name(entity.getName())
                .franchiseId(entity.getFranchiseId())
                .build();
    }

    public static BranchEntity toEntity(Branch domain) {
        return BranchEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .franchiseId(domain.getFranchiseId())
                .build();
    }
}