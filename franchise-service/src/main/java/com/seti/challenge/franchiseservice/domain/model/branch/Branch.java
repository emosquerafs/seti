package com.seti.challenge.franchiseservice.domain.model.branch;

import com.seti.challenge.franchiseservice.domain.model.product.Product;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Branch {
    private UUID id;
    private String name;
    private List<Product> products;
}
