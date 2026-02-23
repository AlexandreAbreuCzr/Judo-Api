package com.alexandre.Judo_Candoi_Api.dto.site;

public record BlogPostDTO(
        String title,
        String slug,
        String excerpt,
        String content,
        String imageUrl
) {
}
