package com.alexandre.Judo_Candoi_Api.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

public record SiteSettingsUpdateDTO(
        @NotBlank(message = "Marca e obrigatoria")
        @Size(max = 120, message = "Marca deve ter no maximo 120 caracteres")
        String brandName,

        @NotBlank(message = "Titulo principal e obrigatorio")
        @Size(max = 200, message = "Titulo principal deve ter no maximo 200 caracteres")
        String heroTitle,

        @NotBlank(message = "Subtitulo principal e obrigatorio")
        @Size(max = 500, message = "Subtitulo principal deve ter no maximo 500 caracteres")
        String heroSubtitle,

        @NotBlank(message = "Frase de impacto e obrigatoria")
        @Size(max = 260, message = "Frase de impacto deve ter no maximo 260 caracteres")
        String impactPhrase,

        @NotBlank(message = "Texto do botao principal e obrigatorio")
        @Size(max = 80, message = "Texto do botao principal deve ter no maximo 80 caracteres")
        String callToActionPrimaryLabel,

        @NotBlank(message = "Link do botao principal e obrigatorio")
        @Size(max = 180, message = "Link do botao principal deve ter no maximo 180 caracteres")
        String callToActionPrimaryUrl,

        @NotBlank(message = "Texto do botao secundario e obrigatorio")
        @Size(max = 80, message = "Texto do botao secundario deve ter no maximo 80 caracteres")
        String callToActionSecondaryLabel,

        @NotBlank(message = "Titulo da secao sobre e obrigatorio")
        @Size(max = 120, message = "Titulo da secao sobre deve ter no maximo 120 caracteres")
        String aboutTitle,

        @NotBlank(message = "Historia da academia e obrigatoria")
        @Size(max = 1200, message = "Historia da academia deve ter no maximo 1200 caracteres")
        String aboutStory,

        @NotBlank(message = "Destaque da secao sobre e obrigatorio")
        @Size(max = 400, message = "Destaque da secao sobre deve ter no maximo 400 caracteres")
        String aboutHighlight,

        @NotBlank(message = "Titulo da aula experimental e obrigatorio")
        @Size(max = 120, message = "Titulo da aula experimental deve ter no maximo 120 caracteres")
        String trialTitle,

        @NotBlank(message = "Descricao da aula experimental e obrigatoria")
        @Size(max = 500, message = "Descricao da aula experimental deve ter no maximo 500 caracteres")
        String trialDescription,

        @NotBlank(message = "Chamada final e obrigatoria")
        @Size(max = 500, message = "Chamada final deve ter no maximo 500 caracteres")
        String finalCallToAction,

        @NotBlank(message = "Contador de alunos e obrigatorio")
        @Size(max = 20, message = "Contador de alunos deve ter no maximo 20 caracteres")
        String counterStudents,

        @NotBlank(message = "Contador de medalhas e obrigatorio")
        @Size(max = 20, message = "Contador de medalhas deve ter no maximo 20 caracteres")
        String counterMedals,

        @NotBlank(message = "Contador de anos e obrigatorio")
        @Size(max = 20, message = "Contador de anos deve ter no maximo 20 caracteres")
        String counterYears,

        @NotNull(message = "Quantidade de competicoes e obrigatoria")
        @Min(value = 0, message = "Quantidade de competicoes nao pode ser negativa")
        Integer medalCompetitions,

        @NotNull(message = "Quantidade de lutas e obrigatoria")
        @Min(value = 0, message = "Quantidade de lutas nao pode ser negativa")
        Integer medalFights,

        @NotNull(message = "Quantidade de medalhas de ouro e obrigatoria")
        @Min(value = 0, message = "Quantidade de medalhas de ouro nao pode ser negativa")
        Integer medalGold,

        @NotNull(message = "Quantidade de medalhas de prata e obrigatoria")
        @Min(value = 0, message = "Quantidade de medalhas de prata nao pode ser negativa")
        Integer medalSilver,

        @NotNull(message = "Quantidade de medalhas de bronze e obrigatoria")
        @Min(value = 0, message = "Quantidade de medalhas de bronze nao pode ser negativa")
        Integer medalBronze,

        @NotBlank(message = "Numero do WhatsApp e obrigatorio")
        @Size(max = 30, message = "Numero do WhatsApp deve ter no maximo 30 caracteres")
        String whatsappNumber,

        @NotBlank(message = "Usuario do Instagram e obrigatorio")
        @Size(max = 80, message = "Usuario do Instagram deve ter no maximo 80 caracteres")
        String instagramHandle,

        @NotBlank(message = "Endereco da academia e obrigatorio")
        @Size(max = 220, message = "Endereco da academia deve ter no maximo 220 caracteres")
        String academyAddress,

        @NotBlank(message = "Link do mapa e obrigatorio")
        @Size(max = 600, message = "Link do mapa deve ter no maximo 600 caracteres")
        String googleMapsEmbed
) {
}
