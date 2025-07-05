package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.branch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("branch")
public class BranchEntity {
    @Id
    private UUID id;
    private String name;
    private UUID franchiseId;
}