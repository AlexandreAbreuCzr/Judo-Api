package com.alexandre.Judo_Candoi_Api.dto.blog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record BlogPostUpsertDTO(
        @NotBlank(message = "Titulo e obrigatorio")
        @Size(max = 140, message = "Titulo deve ter no maximo 140 caracteres")
        String title,

        @NotBlank(message = "Slug e obrigatorio")
        @Size(max = 160, message = "Slug deve ter no maximo 160 caracteres")
        @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "Slug deve seguir o formato texto-com-hifen")
        String slug,

        @NotBlank(message = "Resumo e obrigatorio")
        @Size(max = 400, message = "Resumo deve ter no maximo 400 caracteres")
        String excerpt,

        @Size(max = 12000, message = "Conteudo deve ter no maximo 12000 caracteres")
        String content,

        @Size(max = 2000, message = "URL da imagem deve ter no maximo 2000 caracteres")
        String imageUrl,

        Boolean active,
        Integer displayOrder
) {
}
