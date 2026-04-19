package com.alexandre.Judo_Candoi_Api.dto.pride;

public record PrideStudentAdminResponseDTO(
        Long id,
        String name,
        String achievement,
        String month,
        String imageUrl,
        boolean active,
        int displayOrder
) {
}
