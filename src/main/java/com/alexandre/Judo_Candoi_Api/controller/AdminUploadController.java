package com.alexandre.Judo_Candoi_Api.controller;

import com.alexandre.Judo_Candoi_Api.dto.upload.AdminImageUploadResponseDTO;
import com.alexandre.Judo_Candoi_Api.service.AdminImageUploadService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/api/v1/admin/uploads")
public class AdminUploadController {

    private final AdminImageUploadService uploadService;

    public AdminUploadController(AdminImageUploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdminImageUploadResponseDTO> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folder", required = false) String folder
    ) {
        return ResponseEntity.ok(uploadService.uploadImage(file, folder));
    }
}
