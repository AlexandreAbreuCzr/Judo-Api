package com.alexandre.Judo_Candoi_Api.dto.site;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ScheduleItemDTO(
        @NotBlank(message = "Dia do treino e obrigatorio")
        @Size(max = 80, message = "Dia do treino deve ter no maximo 80 caracteres")
        String day,

        @NotBlank(message = "Horario do treino e obrigatorio")
        @Size(max = 40, message = "Horario do treino deve ter no maximo 40 caracteres")
        String time,

        @NotBlank(message = "Turma do treino e obrigatoria")
        @Size(max = 80, message = "Turma do treino deve ter no maximo 80 caracteres")
        String audience
) {
}
