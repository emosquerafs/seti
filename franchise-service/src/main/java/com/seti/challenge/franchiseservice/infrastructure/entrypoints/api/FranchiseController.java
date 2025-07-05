package com.seti.challenge.franchiseservice.infrastructure.entrypoints.api;


import com.seti.challenge.franchiseservice.application.usecases.franchise.*;
import com.seti.challenge.franchiseservice.domain.model.franchise.Franchise;
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
public class FranchiseController {

    private static final Logger log = LoggerFactory.getLogger(FranchiseController.class);

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final UpdateFranchiseUseCase updateFranchiseUseCase;
    private final DeleteFranchiseUseCase deleteFranchiseUseCase;
    private final GetFranchiseByIdUseCase getFranchiseByIdUseCase;
    private final GetAllFranchisesUseCase getAllFranchisesUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Franchise> create(@RequestBody Franchise franchise) {
        log.info("API: Creating franchise with name={}", franchise.getName());
        return createFranchiseUseCase.execute(franchise);
    }

    @PutMapping("/{id}")
    public Mono<Franchise> update(@PathVariable UUID id, @RequestBody Franchise franchise) {
        log.info("API: Updating franchise with id={}", id);
        return updateFranchiseUseCase.execute(id, franchise);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID id) {
        log.info("API: Deleting franchise with id={}", id);
        return deleteFranchiseUseCase.execute(id);
    }

    @GetMapping("/{id}")
    public Mono<Franchise> getById(@PathVariable UUID id) {
        log.info("API: Getting franchise by id={}", id);
        return getFranchiseByIdUseCase.execute(id);
    }

    @GetMapping
    public Flux<Franchise> getAll() {
        log.info("API: Getting all franchises");
        return getAllFranchisesUseCase.execute();
    }
}
