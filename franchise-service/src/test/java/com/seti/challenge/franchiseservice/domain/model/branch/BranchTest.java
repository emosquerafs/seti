package com.seti.challenge.franchiseservice.domain.model.branch;

import com.seti.challenge.franchiseservice.domain.model.product.Product;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BranchTest {

    @Test
    void testBranchCreation() {
        // Given
        UUID id = UUID.randomUUID();
        UUID franchiseId = UUID.randomUUID();
        String name = "Test Branch";
        List<Product> products = Arrays.asList(
            Product.builder()
                .id(UUID.randomUUID())
                .name("Product 1")
                .stock(10)
                .branchId(id)
                .build()
        );

        // When
        Branch branch = Branch.builder()
            .id(id)
            .name(name)
            .franchiseId(franchiseId)
            .products(products)
            .build();

        // Then
        assertNotNull(branch);
        assertEquals(id, branch.getId());
        assertEquals(name, branch.getName());
        assertEquals(franchiseId, branch.getFranchiseId());
        assertEquals(products, branch.getProducts());
        assertEquals(1, branch.getProducts().size());
    }

    @Test
    void testBranchEqualsAndHashCode() {
        // Given
        UUID id = UUID.randomUUID();
        UUID franchiseId = UUID.randomUUID();
        String name = "Test Branch";
        
        Branch branch1 = Branch.builder()
            .id(id)
            .name(name)
            .franchiseId(franchiseId)
            .build();
            
        Branch branch2 = Branch.builder()
            .id(id)
            .name(name)
            .franchiseId(franchiseId)
            .build();

        // Then
        assertEquals(branch1, branch2);
        assertEquals(branch1.hashCode(), branch2.hashCode());
    }

    @Test
    void testBranchToString() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Test Branch";
        UUID franchiseId = UUID.randomUUID();
        
        Branch branch = Branch.builder()
            .id(id)
            .name(name)
            .franchiseId(franchiseId)
            .build();

        // When
        String toString = branch.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("Test Branch"));
        assertTrue(toString.contains(id.toString()));
        assertTrue(toString.contains(franchiseId.toString()));
    }
}
