package com.seti.challenge.franchiseservice.domain.model.product;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testProductCreation() {
        // Given
        UUID id = UUID.randomUUID();
        UUID branchId = UUID.randomUUID();
        String name = "Test Product";
        int stock = 100;

        // When
        Product product = Product.builder()
            .id(id)
            .name(name)
            .stock(stock)
            .branchId(branchId)
            .build();

        // Then
        assertNotNull(product);
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(stock, product.getStock());
        assertEquals(branchId, product.getBranchId());
    }

    @Test
    void testProductNoArgsConstructor() {
        // When
        Product product = new Product();

        // Then
        assertNotNull(product);
        assertNull(product.getId());
        assertNull(product.getName());
        assertEquals(0, product.getStock());
        assertNull(product.getBranchId());
    }

    @Test
    void testProductAllArgsConstructor() {
        // Given
        UUID id = UUID.randomUUID();
        UUID branchId = UUID.randomUUID();
        String name = "Test Product";
        int stock = 50;

        // When
        Product product = new Product(id, name, stock, branchId);

        // Then
        assertNotNull(product);
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(stock, product.getStock());
        assertEquals(branchId, product.getBranchId());
    }

    @Test
    void testProductEqualsAndHashCode() {
        // Given
        UUID id = UUID.randomUUID();
        UUID branchId = UUID.randomUUID();
        String name = "Test Product";
        int stock = 75;
        
        Product product1 = Product.builder()
            .id(id)
            .name(name)
            .stock(stock)
            .branchId(branchId)
            .build();
            
        Product product2 = Product.builder()
            .id(id)
            .name(name)
            .stock(stock)
            .branchId(branchId)
            .build();

        // Then
        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    void testProductToString() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Test Product";
        int stock = 25;
        UUID branchId = UUID.randomUUID();
        
        Product product = Product.builder()
            .id(id)
            .name(name)
            .stock(stock)
            .branchId(branchId)
            .build();

        // When
        String toString = product.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("Test Product"));
        assertTrue(toString.contains(id.toString()));
        assertTrue(toString.contains("25"));
        assertTrue(toString.contains(branchId.toString()));
    }

    @Test
    void testProductSetters() {
        // Given
        Product product = new Product();
        UUID id = UUID.randomUUID();
        UUID branchId = UUID.randomUUID();
        String name = "Updated Product";
        int stock = 200;

        // When
        product.setId(id);
        product.setName(name);
        product.setStock(stock);
        product.setBranchId(branchId);

        // Then
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(stock, product.getStock());
        assertEquals(branchId, product.getBranchId());
    }
}
