package com.alexandre.Judo_Candoi_Api.dto.pride;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PrideStudentUpsertDTO(
        @NotBlank(message = "Nome e obrigatorio")
        @Size(max = 120, message = "Nome deve ter no maximo 120 caracteres")
        String name,

        @NotBlank(message = "Conquista e obrigatoria")
        @Size(max = 260, message = "Conquista deve ter no maximo 260 caracteres")
        String achievement,

        @NotBlank(message = "Mes e obrigatorio")
        @Size(max = 40, message = "Mes deve ter no maximo 40 caracteres")
        String month,

        @Size(max = 500, message = "URL da imagem deve ter no maximo 500 caracteres")
        String imageUrl,

        Boolean active,
        Integer displayOrder
) {
}
