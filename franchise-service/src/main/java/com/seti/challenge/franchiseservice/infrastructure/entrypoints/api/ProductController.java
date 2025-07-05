package com.seti.challenge.franchiseservice.infrastructure.entrypoints.api;

import com.seti.challenge.franchiseservice.application.usecases.product.*;
import com.seti.challenge.franchiseservice.domain.model.product.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Products", description = "Operaciones CRUD y consultas avanzadas sobre productos en sucursales")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final GetProductsByBranchUseCase getProductsByBranchUseCase;
    private final GetProductWithMaxStockByBranchUseCase getProductWithMaxStockByBranchUseCase;
    private final GetMaxStockProductPerBranchForFranchiseUseCase getMaxStockProductPerBranchForFranchiseUseCase;


    @Operation(
            summary = "Agregar un nuevo producto a una sucursal",
            description = "Agrega un nuevo producto a una sucursal específica.",
            tags = {"Products"},
            parameters = {
                    @Parameter(name = "branchId", description = "ID de la sucursal", example = "48d8ae9c-ff97-42ad-a7a3-9ec31e4b33c5")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Product.class),
                            examples = @ExampleObject(
                                    name = "Producto ejemplo",
                                    value = "{ \"name\": \"Producto Demo\", \"stock\": 100 }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    @PostMapping("/branches/{branchId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> createProduct(@PathVariable UUID branchId, @RequestBody Product product) {
        log.info("API: Creating product {} in branch {}", product.getName(), branchId);
        return createProductUseCase.execute(product, branchId);
    }

    @Operation(
            summary = "Actualizar un producto",
            description = "Actualiza los datos de un producto existente.",
            tags = {"Products"},
            parameters = {
                    @Parameter(name = "productId", description = "ID del producto", example = "e0d84981-2f34-4d8e-94b6-5142b661bea1")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Product.class),
                            examples = @ExampleObject(
                                    name = "Actualización ejemplo",
                                    value = "{ \"name\": \"Producto Actualizado\", \"stock\": 55 }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
            }
    )
    @PutMapping("/products/{productId}")
    public Mono<Product> updateProduct(@PathVariable UUID productId, @RequestBody Product product) {
        log.info("API: Updating product {}", productId);
        return updateProductUseCase.execute(productId, product);
    }

    @Operation(
            summary = "Eliminar un producto",
            description = "Elimina un producto de la base de datos.",
            tags = {"Products"},
            parameters = {
                    @Parameter(name = "productId", description = "ID del producto", example = "e0d84981-2f34-4d8e-94b6-5142b661bea1")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
            }
    )
    @DeleteMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable UUID productId) {
        log.info("API: Deleting product {}", productId);
        return deleteProductUseCase.execute(productId);
    }

    @Operation(
            summary = "Obtener producto por ID",
            description = "Consulta los detalles de un producto usando su ID.",
            tags = {"Products"},
            parameters = {
                    @Parameter(name = "productId", description = "ID del producto", example = "e0d84981-2f34-4d8e-94b6-5142b661bea1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Producto encontrado"),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
            }
    )
    @GetMapping("/products/{productId}")
    public Mono<Product> getProductById(@PathVariable UUID productId) {
        log.info("API: Getting product {}", productId);
        return getProductByIdUseCase.execute(productId);
    }

    @Operation(
            summary = "Listar productos de una sucursal",
            description = "Devuelve todos los productos asociados a una sucursal.",
            tags = {"Products"},
            parameters = {
                    @Parameter(name = "branchId", description = "ID de la sucursal", example = "48d8ae9c-ff97-42ad-a7a3-9ec31e4b33c5")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de productos")
            }
    )
    @GetMapping("/branches/{branchId}/products")
    public Flux<Product> getProductsByBranch(@PathVariable UUID branchId) {
        log.info("API: Getting products for branch {}", branchId);
        return getProductsByBranchUseCase.execute(branchId);
    }

    @Operation(
            summary = "Obtener el producto con más stock en una sucursal",
            description = "Devuelve el producto que más stock tiene en una sucursal específica.",
            tags = {"Products"},
            parameters = {
                    @Parameter(name = "branchId", description = "ID de la sucursal", example = "48d8ae9c-ff97-42ad-a7a3-9ec31e4b33c5")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Producto con más stock encontrado"),
                    @ApiResponse(responseCode = "404", description = "No se encontró producto para la sucursal")
            }
    )
    @GetMapping("/branches/{branchId}/products/max-stock")
    public Mono<Product> getProductWithMaxStockByBranch(@PathVariable UUID branchId) {
        log.info("API: Getting product with max stock for branch {}", branchId);
        return getProductWithMaxStockByBranchUseCase.execute(branchId);
    }

    @Operation(
            summary = "Obtener el producto con más stock por sucursal de una franquicia",
            description = "Devuelve el producto con mayor stock de cada sucursal para una franquicia dada.",
            tags = {"Products"},
            parameters = {
                    @Parameter(name = "franchiseId", description = "ID de la franquicia", example = "d3813e13-49ef-41e5-beb2-70f7cc3338e2")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de productos con más stock por sucursal")
            }
    )
    @GetMapping("/franchises/{franchiseId}/branches/products/max-stock")
    public Flux<Product> getMaxStockProductPerBranchForFranchise(@PathVariable UUID franchiseId) {
        log.info("API: Getting max-stock product per branch for franchise {}", franchiseId);
        return getMaxStockProductPerBranchForFranchiseUseCase.execute(franchiseId);
    }
}
