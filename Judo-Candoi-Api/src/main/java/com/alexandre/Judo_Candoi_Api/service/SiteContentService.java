package com.alexandre.Judo_Candoi_Api.service;

import com.alexandre.Judo_Candoi_Api.dto.site.*;
import com.alexandre.Judo_Candoi_Api.model.SiteSettings;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SiteContentService {

    private final SiteSettingsService siteSettingsService;
    private final BlogPostService blogPostService;
    private final PrideStudentService prideStudentService;
    private final SponsorService sponsorService;

    public SiteContentService(
            SiteSettingsService siteSettingsService,
            BlogPostService blogPostService,
            PrideStudentService prideStudentService,
            SponsorService sponsorService
    ) {
        this.siteSettingsService = siteSettingsService;
        this.blogPostService = blogPostService;
        this.prideStudentService = prideStudentService;
        this.sponsorService = sponsorService;
    }

    public SiteContentResponseDTO getContent() {
        SiteSettings settings = siteSettingsService.getOrCreateEntity();
        String whatsappMessage = "Ola, quero agendar uma aula experimental no Judo Candoi.";
        String whatsappUrl = "https://wa.me/" + settings.getWhatsappNumber() + "?text="
                + URLEncoder.encode(whatsappMessage, StandardCharsets.UTF_8);

        return new SiteContentResponseDTO(
                settings.getBrandName(),
                settings.getHeroTitle(),
                settings.getHeroSubtitle(),
                settings.getImpactPhrase(),
                settings.getCallToActionPrimaryLabel(),
                settings.getCallToActionPrimaryUrl(),
                settings.getCallToActionSecondaryLabel(),
                whatsappUrl,
                settings.getAboutTitle(),
                settings.getAboutStory(),
                settings.getAboutHighlight(),
                List.of(
                        new CounterDTO("alunos ativos", settings.getCounterStudents()),
                        new CounterDTO("medalhas conquistadas", settings.getCounterMedals()),
                        new CounterDTO("anos formando atletas", settings.getCounterYears())
                ),
                new MedalStatsDTO(
                        settings.getMedalCompetitions(),
                        settings.getMedalFights(),
                        settings.getMedalGold(),
                        settings.getMedalSilver(),
                        settings.getMedalBronze()
                ),
                List.of(
                        new ProgramDTO(
                                "Judo Infantil",
                                "4 a 10 anos",
                                List.of("Coordenacao motora", "Disciplina e respeito", "Aulas ludicas e seguras"),
                                "Quero matricular meu filho"
                        ),
                        new ProgramDTO(
                                "Judo Juvenil",
                                "11 a 17 anos",
                                List.of("Foco e autocontrole", "Confianca e autoestima", "Competicao saudavel"),
                                "Quero comecar agora"
                        ),
                        new ProgramDTO(
                                "Judo Adulto",
                                "Iniciante ou praticante",
                                List.of("Condicionamento fisico", "Defesa pessoal", "Qualidade de vida"),
                                "Agendar aula experimental"
                        )
                ),
                List.of(
                        "Progressao por niveis tecnicos",
                        "Avaliacao tecnica continua",
                        "Preparacao fisica e mental",
                        "Treinos adaptados por faixa etaria",
                        "Competicao com proposito, nao so medalha"
                ),
                List.of(
                        new AchievementDTO("Participacoes regionais e estaduais", "Equipe presente em eventos oficiais com constancia"),
                        new AchievementDTO("Evolucao comportamental", "Pais relatam mais disciplina e confianca em casa e na escola"),
                        new AchievementDTO("Podios e destaque tecnico", "Atletas preparados para competir sem perder a essencia educativa")
                ),
                siteSettingsService.resolveGallery(settings),
                siteSettingsService.resolveTestimonials(settings),
                settings.getTrialTitle(),
                settings.getTrialDescription(),
                siteSettingsService.resolveSchedules(settings),
                blogPostService.findPublic(),
                sponsorService.findPublic(),
                List.of(
                        new TimelineStepDTO("Primeiro kimono", "Entrada no tatame com acolhimento e fundamentos basicos."),
                        new TimelineStepDTO("Primeiras faixas", "Evolucao tecnica com metas claras e acompanhamento continuo."),
                        new TimelineStepDTO("Competicao consciente", "Experiencia de campeonato com foco em crescimento pessoal."),
                        new TimelineStepDTO("Orgulho no podio", "Resultado da disciplina construida treino apos treino.")
                ),
                prideStudentService.findPublic(),
                new ContactDTO(
                        settings.getAcademyAddress(),
                        whatsappUrl,
                        "WhatsApp direto",
                        settings.getInstagramHandle(),
                        "https://instagram.com/" + settings.getInstagramHandle().replace("@", ""),
                        settings.getGoogleMapsEmbed()
                ),
                settings.getFinalCallToAction()
        );
    }
}
