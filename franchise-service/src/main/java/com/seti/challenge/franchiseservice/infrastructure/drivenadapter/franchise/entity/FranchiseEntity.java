package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.franchise.entity;

import lombok.*;
        import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("franchise")
public class FranchiseEntity {
    @Id
    private UUID id;
    private String name;
}
