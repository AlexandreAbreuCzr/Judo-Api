package com.alexandre.Judo_Candoi_Api.controller;

import com.alexandre.Judo_Candoi_Api.dto.sponsor.SponsorAdminResponseDTO;
import com.alexandre.Judo_Candoi_Api.dto.sponsor.SponsorUpsertDTO;
import com.alexandre.Judo_Candoi_Api.service.SponsorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/admin/sponsors")
public class SponsorController {

    private final SponsorService service;

    public SponsorController(SponsorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SponsorAdminResponseDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<SponsorAdminResponseDTO> create(@Valid @RequestBody SponsorUpsertDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SponsorAdminResponseDTO> update(
            @PathVariable @Positive Long id,
            @Valid @RequestBody SponsorUpsertDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
