package com.alexandre.Judo_Candoi_Api.dto.blog;

public record BlogPostAdminResponseDTO(
        Long id,
        String title,
        String slug,
        String excerpt,
        String content,
        String imageUrl,
        boolean active,
        int displayOrder
) {
}
