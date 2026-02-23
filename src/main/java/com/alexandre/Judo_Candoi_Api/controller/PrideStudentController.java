package com.alexandre.Judo_Candoi_Api.controller;

import com.alexandre.Judo_Candoi_Api.dto.pride.PrideStudentAdminResponseDTO;
import com.alexandre.Judo_Candoi_Api.dto.pride.PrideStudentUpsertDTO;
import com.alexandre.Judo_Candoi_Api.service.PrideStudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/admin/pride-students")
public class PrideStudentController {

    private final PrideStudentService service;

    public PrideStudentController(PrideStudentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PrideStudentAdminResponseDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<PrideStudentAdminResponseDTO> create(@Valid @RequestBody PrideStudentUpsertDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrideStudentAdminResponseDTO> update(
            @PathVariable @Positive Long id,
            @Valid @RequestBody PrideStudentUpsertDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
