package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.product.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductEntityTest {

    @Test
    void testProductEntityCreation() {
        // Given
        UUID id = UUID.randomUUID();
        UUID branchId = UUID.randomUUID();
        String name = "Test Product Entity";
        int stock = 100;

        // When
        ProductEntity entity = ProductEntity.builder()
            .id(id)
            .name(name)
            .stock(stock)
            .branchId(branchId)
            .build();

        // Then
        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(stock, entity.getStock());
        assertEquals(branchId, entity.getBranchId());
    }

    @Test
    void testProductEntityNoArgsConstructor() {
        // When
        ProductEntity entity = new ProductEntity();

        // Then
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getName());
        assertEquals(0, entity.getStock());
        assertNull(entity.getBranchId());
    }

    @Test
    void testProductEntityAllArgsConstructor() {
        // Given
        UUID id = UUID.randomUUID();
        UUID branchId = UUID.randomUUID();
        String name = "Test Product Entity";
        int stock = 50;

        // When
        ProductEntity entity = new ProductEntity(id, name, stock, branchId);

        // Then
        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(stock, entity.getStock());
        assertEquals(branchId, entity.getBranchId());
    }

    @Test
    void testProductEntityEqualsAndHashCode() {
        // Given
        UUID id = UUID.randomUUID();
        UUID branchId = UUID.randomUUID();
        String name = "Test Product Entity";
        int stock = 25;
        
        ProductEntity entity1 = ProductEntity.builder()
            .id(id)
            .name(name)
            .stock(stock)
            .branchId(branchId)
            .build();
            
        ProductEntity entity2 = ProductEntity.builder()
            .id(id)
            .name(name)
            .stock(stock)
            .branchId(branchId)
            .build();

        // Then
        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    void testProductEntitySetters() {
        // Given
        ProductEntity entity = new ProductEntity();
        UUID id = UUID.randomUUID();
        UUID branchId = UUID.randomUUID();
        String name = "Updated Name";
        int stock = 150;

        // When
        entity.setId(id);
        entity.setName(name);
        entity.setStock(stock);
        entity.setBranchId(branchId);

        // Then
        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(stock, entity.getStock());
        assertEquals(branchId, entity.getBranchId());
    }
}
