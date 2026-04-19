package com.alexandre.Judo_Candoi_Api.dto.site;

public record MedalStatsDTO(
        int competitions,
        int fights,
        int gold,
        int silver,
        int bronze
) {
}
