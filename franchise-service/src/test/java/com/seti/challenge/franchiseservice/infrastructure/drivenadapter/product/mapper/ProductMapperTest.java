package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.product.mapper;

import com.seti.challenge.franchiseservice.domain.model.product.Product;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.product.entity.ProductEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    @Test
    void toDomain_ShouldMapEntityToDomain() {
        // Given
        UUID id = UUID.randomUUID();
        UUID branchId = UUID.randomUUID();
        String name = "Test Product";
        int stock = 50;
        
        ProductEntity entity = ProductEntity.builder()
            .id(id)
            .name(name)
            .stock(stock)
            .branchId(branchId)
            .build();

        // When
        Product domain = ProductMapper.toDomain(entity);

        // Then
        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(name, domain.getName());
        assertEquals(stock, domain.getStock());
        assertEquals(branchId, domain.getBranchId());
    }

    @Test
    void toEntity_ShouldMapDomainToEntity() {
        // Given
        UUID id = UUID.randomUUID();
        UUID branchId = UUID.randomUUID();
        String name = "Test Product";
        int stock = 75;
        
        Product domain = Product.builder()
            .id(id)
            .name(name)
            .stock(stock)
            .branchId(branchId)
            .build();

        // When
        ProductEntity entity = ProductMapper.toEntity(domain);

        // Then
        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(stock, entity.getStock());
        assertEquals(branchId, entity.getBranchId());
    }

    @Test
    void toDomain_ShouldHandleNullFields() {
        // Given
        ProductEntity entity = ProductEntity.builder()
            .id(null)
            .name(null)
            .stock(0)
            .branchId(null)
            .build();

        // When
        Product domain = ProductMapper.toDomain(entity);

        // Then
        assertNotNull(domain);
        assertNull(domain.getId());
        assertNull(domain.getName());
        assertEquals(0, domain.getStock());
        assertNull(domain.getBranchId());
    }

    @Test
    void toEntity_ShouldHandleNullFields() {
        // Given
        Product domain = Product.builder()
            .id(null)
            .name(null)
            .stock(0)
            .branchId(null)
            .build();

        // When
        ProductEntity entity = ProductMapper.toEntity(domain);

        // Then
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getName());
        assertEquals(0, entity.getStock());
        assertNull(entity.getBranchId());
    }
}
