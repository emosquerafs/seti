package com.seti.challenge.franchiseservice.infrastructure.entrypoints.api;


import com.seti.challenge.franchiseservice.application.usecases.franchise.*;
import com.seti.challenge.franchiseservice.domain.model.franchise.Franchise;
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
@RequestMapping(value = "/api/franchises", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Franchises", description = "Operaciones CRUD sobre franquicias")
public class FranchiseController {

    private static final Logger log = LoggerFactory.getLogger(FranchiseController.class);

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final UpdateFranchiseUseCase updateFranchiseUseCase;
    private final DeleteFranchiseUseCase deleteFranchiseUseCase;
    private final GetFranchiseByIdUseCase getFranchiseByIdUseCase;
    private final GetAllFranchisesUseCase getAllFranchisesUseCase;

    @Operation(
            summary = "Crear una nueva franquicia",
            description = "Agrega una nueva franquicia al sistema.",
            tags = {"Franchises"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Franchise.class),
                            examples = @ExampleObject(
                                    name = "Franquicia ejemplo",
                                    value = "{ \"name\": \"Franquicia Centro Comercial\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Franquicia creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Franchise> create(@RequestBody Franchise franchise) {
        log.info("API: Creating franchise with name={}", franchise.getName());
        return createFranchiseUseCase.execute(franchise);
    }

    @Operation(
            summary = "Actualizar franquicia",
            description = "Actualiza la información de una franquicia existente.",
            tags = {"Franchises"},
            parameters = {
                    @Parameter(name = "id", description = "ID de la franquicia", example = "d3813e13-49ef-41e5-beb2-70f7cc3338e2")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Franchise.class),
                            examples = @ExampleObject(
                                    name = "Actualización ejemplo",
                                    value = "{ \"name\": \"Franquicia Norte\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Franquicia actualizada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
            }
    )
    @PutMapping("/{id}")
    public Mono<Franchise> update(@PathVariable UUID id, @RequestBody Franchise franchise) {
        log.info("API: Updating franchise with id={}", id);
        return updateFranchiseUseCase.execute(id, franchise);
    }

    @Operation(
            summary = "Eliminar franquicia",
            description = "Elimina una franquicia por su ID.",
            tags = {"Franchises"},
            parameters = {
                    @Parameter(name = "id", description = "ID de la franquicia", example = "d3813e13-49ef-41e5-beb2-70f7cc3338e2")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Franquicia eliminada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID id) {
        log.info("API: Deleting franchise with id={}", id);
        return deleteFranchiseUseCase.execute(id);
    }

    @Operation(
            summary = "Consultar franquicia por ID",
            description = "Retorna la información de una franquicia específica.",
            tags = {"Franchises"},
            parameters = {
                    @Parameter(name = "id", description = "ID de la franquicia", example = "d3813e13-49ef-41e5-beb2-70f7cc3338e2")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Franquicia encontrada"),
                    @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
            }
    )
    @GetMapping("/{id}")
    public Mono<Franchise> getById(@PathVariable UUID id) {
        log.info("API: Getting franchise by id={}", id);
        return getFranchiseByIdUseCase.execute(id);
    }

    @Operation(
            summary = "Consultar todas las franquicias",
            description = "Obtiene el listado de todas las franquicias registradas.",
            tags = {"Franchises"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de franquicias")
            }
    )
    @GetMapping
    public Flux<Franchise> getAll() {
        log.info("API: Getting all franchises");
        return getAllFranchisesUseCase.execute();
    }
}
