package com.alexandre.Judo_Candoi_Api.service;

import com.alexandre.Judo_Candoi_Api.dto.admin.SiteSettingsAdminResponseDTO;
import com.alexandre.Judo_Candoi_Api.dto.admin.SiteSettingsUpdateDTO;
import com.alexandre.Judo_Candoi_Api.model.SiteSettings;
import com.alexandre.Judo_Candoi_Api.repository.SiteSettingsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SiteSettingsService {

    private final SiteSettingsRepository repository;
    private final String defaultWhatsappNumber;
    private final String defaultInstagramHandle;
    private final String defaultAcademyAddress;
    private final String defaultGoogleMapsEmbed;

    public SiteSettingsService(
            SiteSettingsRepository repository,
            @Value("${app.whatsapp.number:5546999999999}") String defaultWhatsappNumber,
            @Value("${app.instagram.handle:@judocandoi}") String defaultInstagramHandle,
            @Value("${app.academy.address:Avenida Central, 123 - Candoi/PR}") String defaultAcademyAddress,
            @Value("${app.google.maps.embed:https://www.google.com/maps?q=Candoi%20PR&output=embed}") String defaultGoogleMapsEmbed
    ) {
        this.repository = repository;
        this.defaultWhatsappNumber = defaultWhatsappNumber;
        this.defaultInstagramHandle = defaultInstagramHandle;
        this.defaultAcademyAddress = defaultAcademyAddress;
        this.defaultGoogleMapsEmbed = defaultGoogleMapsEmbed;
    }

    @Transactional(readOnly = true)
    public SiteSettingsAdminResponseDTO findAdminSettings() {
        return toDto(getOrCreateEntity());
    }

    @Transactional
    public SiteSettingsAdminResponseDTO update(SiteSettingsUpdateDTO dto) {
        SiteSettings settings = getOrCreateEntity();

        settings.update(
                normalize(dto.brandName()),
                normalize(dto.heroTitle()),
                normalize(dto.heroSubtitle()),
                normalize(dto.impactPhrase()),
                normalize(dto.callToActionPrimaryLabel()),
                normalize(dto.callToActionPrimaryUrl()),
                normalize(dto.callToActionSecondaryLabel()),
                normalize(dto.aboutTitle()),
                normalize(dto.aboutStory()),
                normalize(dto.aboutHighlight()),
                normalize(dto.trialTitle()),
                normalize(dto.trialDescription()),
                normalize(dto.finalCallToAction()),
                normalize(dto.counterStudents()),
                normalize(dto.counterMedals()),
                normalize(dto.counterYears()),
                dto.medalCompetitions(),
                dto.medalFights(),
                dto.medalGold(),
                dto.medalSilver(),
                dto.medalBronze(),
                normalize(dto.whatsappNumber()),
                normalize(dto.instagramHandle()),
                normalize(dto.academyAddress()),
                normalize(dto.googleMapsEmbed())
        );

        return toDto(repository.save(settings));
    }

    @Transactional
    public SiteSettings getOrCreateEntity() {
        return repository.findTopByOrderByIdAsc()
                .orElseGet(() -> repository.save(createDefault()));
    }

    private SiteSettings createDefault() {
        return new SiteSettings(
                "JUDO CANDOI",
                "Mais que judo. Disciplina para a vida.",
                "Formamos pessoas, fortalecemos valores e construimos campeoes dentro e fora do tatame.",
                "Judo Candoi nao e so academia. E formacao de carater, disciplina e campeoes.",
                "Agendar aula experimental",
                "#aula-experimental",
                "Falar no WhatsApp",
                "Quem somos",
                "O Judo Candoi nasceu com um proposito claro: usar o judo como ferramenta de transformacao. Cada aluno e acompanhado de perto, respeitando idade, ritmo e objetivos.",
                "Aqui formamos pessoas antes de atletas.",
                "Aula experimental gratuita",
                "Sem compromisso, para todas as idades. Venha conhecer nossa metodologia no tatame.",
                "Seu filho merece mais que um esporte. Merece disciplina, valores e confianca.",
                "+120",
                "+85",
                "+12",
                36,
                478,
                21,
                24,
                42,
                defaultWhatsappNumber,
                defaultInstagramHandle,
                defaultAcademyAddress,
                defaultGoogleMapsEmbed
        );
    }

    private SiteSettingsAdminResponseDTO toDto(SiteSettings settings) {
        return new SiteSettingsAdminResponseDTO(
                settings.getId(),
                settings.getBrandName(),
                settings.getHeroTitle(),
                settings.getHeroSubtitle(),
                settings.getImpactPhrase(),
                settings.getCallToActionPrimaryLabel(),
                settings.getCallToActionPrimaryUrl(),
                settings.getCallToActionSecondaryLabel(),
                settings.getAboutTitle(),
                settings.getAboutStory(),
                settings.getAboutHighlight(),
                settings.getTrialTitle(),
                settings.getTrialDescription(),
                settings.getFinalCallToAction(),
                settings.getCounterStudents(),
                settings.getCounterMedals(),
                settings.getCounterYears(),
                settings.getMedalCompetitions(),
                settings.getMedalFights(),
                settings.getMedalGold(),
                settings.getMedalSilver(),
                settings.getMedalBronze(),
                settings.getWhatsappNumber(),
                settings.getInstagramHandle(),
                settings.getAcademyAddress(),
                settings.getGoogleMapsEmbed()
        );
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }
}
