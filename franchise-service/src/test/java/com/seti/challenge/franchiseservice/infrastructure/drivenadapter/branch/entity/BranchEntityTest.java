package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.branch.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BranchEntityTest {

    @Test
    void testBranchEntityCreation() {
        // Given
        UUID id = UUID.randomUUID();
        UUID franchiseId = UUID.randomUUID();
        String name = "Test Branch Entity";

        // When
        BranchEntity entity = BranchEntity.builder()
            .id(id)
            .name(name)
            .franchiseId(franchiseId)
            .build();

        // Then
        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(franchiseId, entity.getFranchiseId());
    }

    @Test
    void testBranchEntityNoArgsConstructor() {
        // When
        BranchEntity entity = new BranchEntity();

        // Then
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getName());
        assertNull(entity.getFranchiseId());
    }

    @Test
    void testBranchEntityAllArgsConstructor() {
        // Given
        UUID id = UUID.randomUUID();
        UUID franchiseId = UUID.randomUUID();
        String name = "Test Branch Entity";

        // When
        BranchEntity entity = new BranchEntity(id, name, franchiseId);

        // Then
        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(franchiseId, entity.getFranchiseId());
    }

    @Test
    void testBranchEntityEqualsAndHashCode() {
        // Given
        UUID id = UUID.randomUUID();
        UUID franchiseId = UUID.randomUUID();
        String name = "Test Branch Entity";
        
        BranchEntity entity1 = BranchEntity.builder()
            .id(id)
            .name(name)
            .franchiseId(franchiseId)
            .build();
            
        BranchEntity entity2 = BranchEntity.builder()
            .id(id)
            .name(name)
            .franchiseId(franchiseId)
            .build();

        // Then
        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    void testBranchEntitySetters() {
        // Given
        BranchEntity entity = new BranchEntity();
        UUID id = UUID.randomUUID();
        UUID franchiseId = UUID.randomUUID();
        String name = "Updated Name";

        // When
        entity.setId(id);
        entity.setName(name);
        entity.setFranchiseId(franchiseId);

        // Then
        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(franchiseId, entity.getFranchiseId());
    }
}
