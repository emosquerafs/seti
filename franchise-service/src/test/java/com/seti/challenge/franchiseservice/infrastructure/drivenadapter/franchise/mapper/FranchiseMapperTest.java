package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.franchise.mapper;

import com.seti.challenge.franchiseservice.domain.model.franchise.Franchise;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.franchise.entity.FranchiseEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FranchiseMapperTest {

    @Test
    void toDomain_ShouldMapEntityToDomain() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Test Franchise";
        
        FranchiseEntity entity = FranchiseEntity.builder()
            .id(id)
            .name(name)
            .build();

        // When
        Franchise domain = FranchiseMapper.toDomain(entity);

        // Then
        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(name, domain.getName());
    }

    @Test
    void toEntity_ShouldMapDomainToEntity() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Test Franchise";
        
        Franchise domain = Franchise.builder()
            .id(id)
            .name(name)
            .build();

        // When
        FranchiseEntity entity = FranchiseMapper.toEntity(domain);

        // Then
        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
    }

    @Test
    void toDomain_ShouldHandleNullFields() {
        // Given
        FranchiseEntity entity = FranchiseEntity.builder()
            .id(null)
            .name(null)
            .build();

        // When
        Franchise domain = FranchiseMapper.toDomain(entity);

        // Then
        assertNotNull(domain);
        assertNull(domain.getId());
        assertNull(domain.getName());
    }

    @Test
    void toEntity_ShouldHandleNullFields() {
        // Given
        Franchise domain = Franchise.builder()
            .id(null)
            .name(null)
            .build();

        // When
        FranchiseEntity entity = FranchiseMapper.toEntity(domain);

        // Then
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getName());
    }
}
