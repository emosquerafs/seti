package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.branch.mapper;

import com.seti.challenge.franchiseservice.domain.model.branch.Branch;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.branch.entity.BranchEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BranchMapperTest {

    @Test
    void toDomain_ShouldMapEntityToDomain() {
        // Given
        UUID id = UUID.randomUUID();
        UUID franchiseId = UUID.randomUUID();
        String name = "Test Branch";
        
        BranchEntity entity = BranchEntity.builder()
            .id(id)
            .name(name)
            .franchiseId(franchiseId)
            .build();

        // When
        Branch domain = BranchMapper.toDomain(entity);

        // Then
        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(name, domain.getName());
        assertEquals(franchiseId, domain.getFranchiseId());
    }

    @Test
    void toEntity_ShouldMapDomainToEntity() {
        // Given
        UUID id = UUID.randomUUID();
        UUID franchiseId = UUID.randomUUID();
        String name = "Test Branch";
        
        Branch domain = Branch.builder()
            .id(id)
            .name(name)
            .franchiseId(franchiseId)
            .build();

        // When
        BranchEntity entity = BranchMapper.toEntity(domain);

        // Then
        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(franchiseId, entity.getFranchiseId());
    }

    @Test
    void toDomain_ShouldHandleNullFields() {
        // Given
        BranchEntity entity = BranchEntity.builder()
            .id(null)
            .name(null)
            .franchiseId(null)
            .build();

        // When
        Branch domain = BranchMapper.toDomain(entity);

        // Then
        assertNotNull(domain);
        assertNull(domain.getId());
        assertNull(domain.getName());
        assertNull(domain.getFranchiseId());
    }

    @Test
    void toEntity_ShouldHandleNullFields() {
        // Given
        Branch domain = Branch.builder()
            .id(null)
            .name(null)
            .franchiseId(null)
            .build();

        // When
        BranchEntity entity = BranchMapper.toEntity(domain);

        // Then
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getName());
        assertNull(entity.getFranchiseId());
    }
}
