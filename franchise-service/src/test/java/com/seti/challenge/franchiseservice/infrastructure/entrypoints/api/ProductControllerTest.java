package com.seti.challenge.franchiseservice.infrastructure.entrypoints.api;

import com.seti.challenge.franchiseservice.application.usecases.product.*;
import com.seti.challenge.franchiseservice.domain.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private CreateProductUseCase createProductUseCase;

    @Mock
    private UpdateProductUseCase updateProductUseCase;

    @Mock
    private DeleteProductUseCase deleteProductUseCase;

    @Mock
    private GetProductByIdUseCase getProductByIdUseCase;

    @Mock
    private GetProductsByBranchUseCase getProductsByBranchUseCase;

    @Mock
    private GetProductWithMaxStockByBranchUseCase getProductWithMaxStockByBranchUseCase;

    @Mock
    private GetMaxStockProductPerBranchForFranchiseUseCase getMaxStockProductPerBranchForFranchiseUseCase;

    @InjectMocks
    private ProductController productController;

    private Product testProduct;
    private UUID testProductId;
    private UUID testBranchId;

    @BeforeEach
    void setUp() {
        testProductId = UUID.randomUUID();
        testBranchId = UUID.randomUUID();
        testProduct = Product.builder()
            .id(testProductId)
            .name("Test Product")
            .stock(100)
            .branchId(testBranchId)
            .build();
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() {
        // Given
        when(createProductUseCase.execute(any(Product.class), eq(testBranchId)))
            .thenReturn(Mono.just(testProduct));

        // When & Then
        StepVerifier.create(productController.createProduct(testBranchId, testProduct))
            .expectNext(testProduct)
            .verifyComplete();
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() {
        // Given
        when(updateProductUseCase.execute(eq(testProductId), any(Product.class)))
            .thenReturn(Mono.just(testProduct));

        // When & Then
        StepVerifier.create(productController.updateProduct(testProductId, testProduct))
            .expectNext(testProduct)
            .verifyComplete();
    }

    @Test
    void deleteProduct_ShouldReturnVoid() {
        // Given
        when(deleteProductUseCase.execute(testProductId))
            .thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(productController.deleteProduct(testProductId))
            .verifyComplete();
    }

    @Test
    void getProductById_ShouldReturnProduct() {
        // Given
        when(getProductByIdUseCase.execute(testProductId))
            .thenReturn(Mono.just(testProduct));

        // When & Then
        StepVerifier.create(productController.getProductById(testProductId))
            .expectNext(testProduct)
            .verifyComplete();
    }

    @Test
    void getProductsByBranch_ShouldReturnProducts() {
        // Given
        Product product2 = Product.builder()
            .id(UUID.randomUUID())
            .name("Another Product")
            .stock(50)
            .branchId(testBranchId)
            .build();
        
        when(getProductsByBranchUseCase.execute(testBranchId))
            .thenReturn(Flux.just(testProduct, product2));

        // When & Then
        StepVerifier.create(productController.getProductsByBranch(testBranchId))
            .expectNext(testProduct)
            .expectNext(product2)
            .verifyComplete();
    }

    @Test
    void getProductWithMaxStockByBranch_ShouldReturnProduct() {
        // Given
        when(getProductWithMaxStockByBranchUseCase.execute(testBranchId))
            .thenReturn(Mono.just(testProduct));

        // When & Then
        StepVerifier.create(productController.getProductWithMaxStockByBranch(testBranchId))
            .expectNext(testProduct)
            .verifyComplete();
    }
}
