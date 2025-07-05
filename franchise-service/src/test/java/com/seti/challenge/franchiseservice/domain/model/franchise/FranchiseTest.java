package com.seti.challenge.franchiseservice.domain.model.franchise;

import com.seti.challenge.franchiseservice.domain.model.branch.Branch;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FranchiseTest {

    @Test
    void testFranchiseCreation() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Test Franchise";
        List<Branch> branches = Arrays.asList(
            Branch.builder()
                .id(UUID.randomUUID())
                .name("Branch 1")
                .franchiseId(id)
                .build()
        );

        // When
        Franchise franchise = Franchise.builder()
            .id(id)
            .name(name)
            .branches(branches)
            .build();

        // Then
        assertNotNull(franchise);
        assertEquals(id, franchise.getId());
        assertEquals(name, franchise.getName());
        assertEquals(branches, franchise.getBranches());
        assertEquals(1, franchise.getBranches().size());
    }

    @Test
    void testFranchiseEqualsAndHashCode() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Test Franchise";
        
        Franchise franchise1 = Franchise.builder()
            .id(id)
            .name(name)
            .build();
            
        Franchise franchise2 = Franchise.builder()
            .id(id)
            .name(name)
            .build();

        // Then
        assertEquals(franchise1, franchise2);
        assertEquals(franchise1.hashCode(), franchise2.hashCode());
    }

    @Test
    void testFranchiseToString() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Test Franchise";
        
        Franchise franchise = Franchise.builder()
            .id(id)
            .name(name)
            .build();

        // When
        String toString = franchise.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("Test Franchise"));
        assertTrue(toString.contains(id.toString()));
    }
}
