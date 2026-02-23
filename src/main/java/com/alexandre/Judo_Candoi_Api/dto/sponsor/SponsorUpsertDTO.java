package com.alexandre.Judo_Candoi_Api.dto.sponsor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SponsorUpsertDTO(
        @NotBlank(message = "Nome do patrocinador e obrigatorio")
        @Size(max = 120, message = "Nome deve ter no maximo 120 caracteres")
        String name,

        @NotBlank(message = "Descricao e obrigatoria")
        @Size(max = 1000, message = "Descricao deve ter no maximo 1000 caracteres")
        String description,

        @NotBlank(message = "URL da logo e obrigatoria")
        @Size(max = 2000, message = "URL da logo deve ter no maximo 2000 caracteres")
        String logoUrl,

        @Size(max = 2000, message = "URL do site deve ter no maximo 2000 caracteres")
        String websiteUrl,

        Boolean active,
        Integer displayOrder
) {
}
