package com.alexandre.Judo_Candoi_Api.dto.sponsor;

public record SponsorAdminResponseDTO(
        Long id,
        String name,
        String description,
        String logoUrl,
        String websiteUrl,
        boolean active,
        int displayOrder
) {
}
