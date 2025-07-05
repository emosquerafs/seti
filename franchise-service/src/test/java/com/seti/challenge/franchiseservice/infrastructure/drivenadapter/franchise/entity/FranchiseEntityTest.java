package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.franchise.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FranchiseEntityTest {

    @Test
    void testFranchiseEntityCreation() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Test Franchise Entity";

        // When
        FranchiseEntity entity = FranchiseEntity.builder()
            .id(id)
            .name(name)
            .build();

        // Then
        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
    }

    @Test
    void testFranchiseEntityNoArgsConstructor() {
        // When
        FranchiseEntity entity = new FranchiseEntity();

        // Then
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getName());
    }

    @Test
    void testFranchiseEntityAllArgsConstructor() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Test Franchise Entity";

        // When
        FranchiseEntity entity = new FranchiseEntity(id, name);

        // Then
        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
    }

    @Test
    void testFranchiseEntityEqualsAndHashCode() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Test Franchise Entity";
        
        FranchiseEntity entity1 = FranchiseEntity.builder()
            .id(id)
            .name(name)
            .build();
            
        FranchiseEntity entity2 = FranchiseEntity.builder()
            .id(id)
            .name(name)
            .build();

        // Then
        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    void testFranchiseEntityToString() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Test Franchise Entity";
        
        FranchiseEntity entity = FranchiseEntity.builder()
            .id(id)
            .name(name)
            .build();

        // When
        String toString = entity.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("Test Franchise Entity"));
        assertTrue(toString.contains(id.toString()));
    }

    @Test
    void testFranchiseEntitySetters() {
        // Given
        FranchiseEntity entity = new FranchiseEntity();
        UUID id = UUID.randomUUID();
        String name = "Updated Name";

        // When
        entity.setId(id);
        entity.setName(name);

        // Then
        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
    }
}
