package com.alexandre.Judo_Candoi_Api.controller;

import com.alexandre.Judo_Candoi_Api.dto.blog.BlogPostAdminResponseDTO;
import com.alexandre.Judo_Candoi_Api.dto.blog.BlogPostUpsertDTO;
import com.alexandre.Judo_Candoi_Api.service.BlogPostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/admin/blog-posts")
public class BlogPostController {

    private final BlogPostService service;

    public BlogPostController(BlogPostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BlogPostAdminResponseDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<BlogPostAdminResponseDTO> create(@Valid @RequestBody BlogPostUpsertDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPostAdminResponseDTO> update(
            @PathVariable @Positive Long id,
            @Valid @RequestBody BlogPostUpsertDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
