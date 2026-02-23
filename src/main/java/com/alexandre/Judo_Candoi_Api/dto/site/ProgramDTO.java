package com.alexandre.Judo_Candoi_Api.dto.site;

import java.util.List;

public record ProgramDTO(
        String title,
        String ageRange,
        List<String> highlights,
        String ctaText
) {
}
