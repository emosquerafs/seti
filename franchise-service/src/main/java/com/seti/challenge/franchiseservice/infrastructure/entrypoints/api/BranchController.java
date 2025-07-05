package com.seti.challenge.franchiseservice.infrastructure.entrypoints.api;

import com.seti.challenge.franchiseservice.application.usecases.branch.*;
import com.seti.challenge.franchiseservice.domain.model.branch.Branch;
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
@RequestMapping(value = "/api/branches", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Branches", description = "Operaciones CRUD sobre sucursales")
public class BranchController {

    private static final Logger log = LoggerFactory.getLogger(BranchController.class);

    private final CreateBranchUseCase createBranchUseCase;
    private final UpdateBranchUseCase updateBranchUseCase;
    private final DeleteBranchUseCase deleteBranchUseCase;
    private final GetBranchByIdUseCase getBranchByIdUseCase;
    private final GetBranchesByFranchiseUseCase getBranchesByFranchiseUseCase;


    @Operation(
            summary = "Agregar una nueva sucursal a una franquicia",
            description = "Crea una nueva sucursal para la franquicia especificada.",
            tags = {"Branches"},
            parameters = {
                    @Parameter(name = "franchiseId", description = "ID de la franquicia", example = "d3813e13-49ef-41e5-beb2-70f7cc3338e2")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Branch.class),
                            examples = @ExampleObject(
                                    name = "Sucursal ejemplo",
                                    value = "{ \"name\": \"Sucursal Centro\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Sucursal creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    @PostMapping("/franchises/{franchiseId}/branches")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Branch> createBranch(@PathVariable UUID franchiseId, @RequestBody Branch branch) {
        log.info("API: Creating branch {} under franchise {}", branch.getName(), franchiseId);
        return createBranchUseCase.execute(branch, franchiseId);
    }


    @Operation(
            summary = "Actualizar sucursal",
            description = "Actualiza la información de una sucursal.",
            tags = {"Branches"},
            parameters = {
                    @Parameter(name = "branchId", description = "ID de la sucursal", example = "e3cbb8ad-b12b-4b13-8459-ff4d51fa82d7")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Branch.class),
                            examples = @ExampleObject(
                                    name = "Actualización ejemplo",
                                    value = "{ \"name\": \"Sucursal Sur\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
            }
    )
    @PutMapping("/branches/{branchId}")
    public Mono<Branch> updateBranch(@PathVariable UUID branchId, @RequestBody Branch branch) {
        log.info("API: Updating branch {}", branchId);
        return updateBranchUseCase.execute(branchId, branch);
    }


    @Operation(
            summary = "Eliminar sucursal",
            description = "Elimina una sucursal por su ID.",
            tags = {"Branches"},
            parameters = {
                    @Parameter(name = "branchId", description = "ID de la sucursal", example = "e3cbb8ad-b12b-4b13-8459-ff4d51fa82d7")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sucursal eliminada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
            }
    )
    @DeleteMapping("/branches/{branchId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBranch(@PathVariable UUID branchId) {
        log.info("API: Deleting branch {}", branchId);
        return deleteBranchUseCase.execute(branchId);
    }


    @Operation(
            summary = "Consultar sucursal por ID",
            description = "Retorna la información de una sucursal específica.",
            tags = {"Branches"},
            parameters = {
                    @Parameter(name = "branchId", description = "ID de la sucursal", example = "e3cbb8ad-b12b-4b13-8459-ff4d51fa82d7")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucursal encontrada"),
                    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
            }
    )
    @GetMapping("/branches/{branchId}")
    public Mono<Branch> getBranchById(@PathVariable UUID branchId) {
        log.info("API: Getting branch {}", branchId);
        return getBranchByIdUseCase.execute(branchId);
    }


    @Operation(
            summary = "Listar sucursales de una franquicia",
            description = "Devuelve todas las sucursales asociadas a una franquicia.",
            tags = {"Branches"},
            parameters = {
                    @Parameter(name = "franchiseId", description = "ID de la franquicia", example = "d3813e13-49ef-41e5-beb2-70f7cc3338e2")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de sucursales")
            }
    )
    @GetMapping("/franchises/{franchiseId}/branches")
    public Flux<Branch> getBranchesByFranchise(@PathVariable UUID franchiseId) {
        log.info("API: Getting branches for franchise {}", franchiseId);
        return getBranchesByFranchiseUseCase.execute(franchiseId);
    }
}
