package com.alexandre.Judo_Candoi_Api.controller;

import com.alexandre.Judo_Candoi_Api.service.AdminImageUploadService;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/uploads/images")
public class UploadedImageController {

    private final AdminImageUploadService uploadService;

    public UploadedImageController(AdminImageUploadService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long imageId) {
        return buildImageResponse(imageId);
    }

    @GetMapping("/{imageId}/{fileName:.+}")
    public ResponseEntity<byte[]> getImageWithName(
            @PathVariable Long imageId,
            @PathVariable String fileName
    ) {
        return buildImageResponse(imageId);
    }

    private ResponseEntity<byte[]> buildImageResponse(Long imageId) {
        AdminImageUploadService.StoredImagePayload image = uploadService.getImageById(imageId);
        MediaType mediaType = resolveMediaType(image.contentType());

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(Duration.ofDays(30)).cachePublic())
                .contentType(mediaType)
                .contentLength(image.sizeInBytes())
                .body(image.bytes());
    }

    private MediaType resolveMediaType(String contentType) {
        if (contentType == null || contentType.isBlank()) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }

        try {
            return MediaType.parseMediaType(contentType);
        } catch (IllegalArgumentException _error) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
