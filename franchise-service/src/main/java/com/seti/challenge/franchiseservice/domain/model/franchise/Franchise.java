package com.seti.challenge.franchiseservice.domain.model.franchise;

import com.seti.challenge.franchiseservice.domain.model.branch.Branch;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Franchise {
    private UUID id;
    private String name;
    private List<Branch> branches;
}
