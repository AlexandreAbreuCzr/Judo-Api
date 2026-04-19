package com.alexandre.Judo_Candoi_Api.dto.lead;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ExperimentalClassLeadRequestDTO(
        @NotBlank(message = "Nome e obrigatorio")
        @Size(min = 2, max = 120, message = "Nome deve ter entre 2 e 120 caracteres")
        String name,

        @NotNull(message = "Idade e obrigatoria")
        @Min(value = 4, message = "Idade minima permitida e 4 anos")
        @Max(value = 80, message = "Idade maxima permitida e 80 anos")
        Integer age,

        @NotBlank(message = "Telefone/WhatsApp e obrigatorio")
        @Pattern(
                regexp = "^[0-9+()\\-\\s]{8,20}$",
                message = "Telefone/WhatsApp invalido"
        )
        String phone,

        @Size(max = 250, message = "Objetivo deve ter no maximo 250 caracteres")
        String objective
) {
}
