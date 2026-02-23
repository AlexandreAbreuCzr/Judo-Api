package com.alexandre.Judo_Candoi_Api.dto.upload;

public record AdminImageUploadResponseDTO(
        String url,
        String fileName,
        long sizeInBytes
) {
}

