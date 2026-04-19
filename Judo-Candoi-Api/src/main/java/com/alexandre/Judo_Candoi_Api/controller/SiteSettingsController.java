package com.alexandre.Judo_Candoi_Api.controller;

import com.alexandre.Judo_Candoi_Api.dto.admin.SiteSettingsAdminResponseDTO;
import com.alexandre.Judo_Candoi_Api.dto.admin.SiteSettingsUpdateDTO;
import com.alexandre.Judo_Candoi_Api.service.SiteSettingsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/site-settings")
public class SiteSettingsController {

    private final SiteSettingsService service;

    public SiteSettingsController(SiteSettingsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<SiteSettingsAdminResponseDTO> get() {
        return ResponseEntity.ok(service.findAdminSettings());
    }

    @PutMapping
    public ResponseEntity<SiteSettingsAdminResponseDTO> update(@Valid @RequestBody SiteSettingsUpdateDTO dto) {
        return ResponseEntity.ok(service.update(dto));
    }
}
