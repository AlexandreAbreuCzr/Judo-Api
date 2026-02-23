package com.alexandre.Judo_Candoi_Api.dto.lead;

import java.time.LocalDateTime;

public record ExperimentalClassLeadResponseDTO(
        long id,
        String name,
        Integer age,
        String phone,
        String objective,
        LocalDateTime createdAt,
        String message
) {
}
