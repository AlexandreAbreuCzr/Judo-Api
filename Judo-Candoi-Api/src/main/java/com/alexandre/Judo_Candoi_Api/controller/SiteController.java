package com.alexandre.Judo_Candoi_Api.controller;

import com.alexandre.Judo_Candoi_Api.dto.site.SiteContentResponseDTO;
import com.alexandre.Judo_Candoi_Api.service.SiteContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/site")
public class SiteController {

    private final SiteContentService siteContentService;

    public SiteController(SiteContentService siteContentService) {
        this.siteContentService = siteContentService;
    }

    @GetMapping("/content")
    public ResponseEntity<SiteContentResponseDTO> content() {
        return ResponseEntity.ok(siteContentService.getContent());
    }
}
