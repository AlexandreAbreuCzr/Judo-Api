package com.alexandre.Judo_Candoi_Api.service;

import com.alexandre.Judo_Candoi_Api.dto.admin.SiteSettingsAdminResponseDTO;
import com.alexandre.Judo_Candoi_Api.dto.admin.SiteSettingsUpdateDTO;
import com.alexandre.Judo_Candoi_Api.dto.site.GalleryItemDTO;
import com.alexandre.Judo_Candoi_Api.dto.site.ScheduleItemDTO;
import com.alexandre.Judo_Candoi_Api.dto.site.TestimonialDTO;
import com.alexandre.Judo_Candoi_Api.model.SiteSettings;
import com.alexandre.Judo_Candoi_Api.repository.SiteSettingsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
public class SiteSettingsService {

    private final SiteSettingsRepository repository;
    private final ObjectMapper objectMapper;
    private final String defaultWhatsappNumber;
    private final String defaultInstagramHandle;
    private final String defaultAcademyAddress;
    private final String defaultGoogleMapsEmbed;

    public SiteSettingsService(
            SiteSettingsRepository repository,
            ObjectMapper objectMapper,
            @Value("${app.whatsapp.number:5546999999999}") String defaultWhatsappNumber,
            @Value("${app.instagram.handle:@judocandoi}") String defaultInstagramHandle,
            @Value("${app.academy.address:Avenida Central, 123 - Candoi/PR}") String defaultAcademyAddress,
            @Value("${app.google.maps.embed:https://www.google.com/maps?q=Candoi%20PR&output=embed}") String defaultGoogleMapsEmbed
    ) {
        this.repository = repository;
        this.objectMapper = objectMapper;
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
                normalize(dto.googleMapsEmbed()),
                toSchedulesJson(normalizeSchedules(dto.schedules())),
                toGalleryJson(normalizeGallery(dto.gallery())),
                toTestimonialsJson(normalizeTestimonials(dto.testimonials()))
        );

        return toDto(repository.save(settings));
    }

    @Transactional
    public SiteSettings getOrCreateEntity() {
        return repository.findTopByOrderByIdAsc()
                .orElseGet(() -> repository.save(createDefault()));
    }

    public List<ScheduleItemDTO> resolveSchedules(SiteSettings settings) {
        String rawSchedules = settings.getSchedulesJson();

        if (rawSchedules == null || rawSchedules.isBlank()) {
            return defaultSchedules();
        }

        try {
            ScheduleItemDTO[] parsedSchedules = objectMapper.readValue(rawSchedules, ScheduleItemDTO[].class);
            return normalizeSchedules(Arrays.asList(parsedSchedules));
        } catch (JsonProcessingException _error) {
            return defaultSchedules();
        }
    }

    public List<GalleryItemDTO> resolveGallery(SiteSettings settings) {
        String rawGallery = settings.getGalleryJson();

        if (rawGallery == null || rawGallery.isBlank()) {
            return defaultGallery();
        }

        try {
            GalleryItemDTO[] parsedGallery = objectMapper.readValue(rawGallery, GalleryItemDTO[].class);
            return normalizeGallery(Arrays.asList(parsedGallery));
        } catch (JsonProcessingException _error) {
            return defaultGallery();
        }
    }

    public List<TestimonialDTO> resolveTestimonials(SiteSettings settings) {
        String rawTestimonials = settings.getTestimonialsJson();

        if (rawTestimonials == null || rawTestimonials.isBlank()) {
            return defaultTestimonials();
        }

        try {
            TestimonialDTO[] parsedTestimonials = objectMapper.readValue(rawTestimonials, TestimonialDTO[].class);
            return normalizeTestimonials(Arrays.asList(parsedTestimonials));
        } catch (JsonProcessingException _error) {
            return defaultTestimonials();
        }
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
                defaultGoogleMapsEmbed,
                toSchedulesJson(defaultSchedules()),
                toGalleryJson(defaultGallery()),
                toTestimonialsJson(defaultTestimonials())
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
                settings.getGoogleMapsEmbed(),
                resolveSchedules(settings),
                resolveGallery(settings),
                resolveTestimonials(settings)
        );
    }

    private List<ScheduleItemDTO> defaultSchedules() {
        return List.of(
                new ScheduleItemDTO("Segunda e Quarta", "17:30 - 18:30", "Infantil"),
                new ScheduleItemDTO("Terca e Quinta", "18:00 - 19:00", "Adolescente"),
                new ScheduleItemDTO("Terca e Quinta", "19:00 - 20:00", "Adultos"),
                new ScheduleItemDTO("Terca", "17:10", "Baby"),
                new ScheduleItemDTO("Sexta", "17:30", "Baby")
        );
    }

    private List<GalleryItemDTO> defaultGallery() {
        return IntStream.rangeClosed(1, 34)
                .mapToObj(index -> {
                    String padded = String.format("%03d", index);
                    String category = switch (index % 3) {
                        case 1 -> "Treinos";
                        case 2 -> "Campeonatos";
                        default -> "Podio";
                    };

                    return new GalleryItemDTO(
                            "Registro de atleta " + padded,
                            "/images/athletes/athlete-" + padded + ".jpeg",
                            category
                    );
                })
                .toList();
    }

    private List<TestimonialDTO> defaultTestimonials() {
        return List.of(
                new TestimonialDTO("Depois do judo, meu filho ficou mais disciplinado e confiante.", "Marina S.", "Mae de aluno infantil"),
                new TestimonialDTO("Aqui nao e so esporte, e educacao.", "Carlos A.", "Pai de atleta juvenil"),
                new TestimonialDTO("O Judo Candoi mudou minha forma de encarar desafios.", "Lucas M.", "Aluno adulto")
        );
    }

    private List<ScheduleItemDTO> normalizeSchedules(List<ScheduleItemDTO> schedules) {
        if (schedules == null || schedules.isEmpty()) {
            return defaultSchedules();
        }

        List<ScheduleItemDTO> normalizedSchedules = schedules.stream()
                .filter(Objects::nonNull)
                .map(schedule -> new ScheduleItemDTO(
                        normalize(schedule.day()),
                        normalize(schedule.time()),
                        normalize(schedule.audience())
                ))
                .filter(schedule -> !schedule.day().isBlank()
                        && !schedule.time().isBlank()
                        && !schedule.audience().isBlank())
                .toList();

        return normalizedSchedules.isEmpty() ? defaultSchedules() : normalizedSchedules;
    }

    private List<GalleryItemDTO> normalizeGallery(List<GalleryItemDTO> gallery) {
        if (gallery == null || gallery.isEmpty()) {
            return List.of();
        }

        return IntStream.range(0, gallery.size())
                .mapToObj(index -> toNormalizedGalleryItem(gallery.get(index), index))
                .filter(Objects::nonNull)
                .toList();
    }

    private List<TestimonialDTO> normalizeTestimonials(List<TestimonialDTO> testimonials) {
        if (testimonials == null || testimonials.isEmpty()) {
            return List.of();
        }

        return IntStream.range(0, testimonials.size())
                .mapToObj(index -> toNormalizedTestimonial(testimonials.get(index), index))
                .filter(Objects::nonNull)
                .toList();
    }

    private GalleryItemDTO toNormalizedGalleryItem(GalleryItemDTO item, int index) {
        if (item == null) {
            return null;
        }

        String imageUrl = normalize(item.imageUrl());
        if (imageUrl.isBlank()) {
            return null;
        }

        String title = normalize(item.title());
        String category = normalize(item.category());

        return new GalleryItemDTO(
                title.isBlank() ? "Registro de atleta " + String.format("%02d", index + 1) : title,
                imageUrl,
                category.isBlank() ? "Atletas" : category
        );
    }

    private TestimonialDTO toNormalizedTestimonial(TestimonialDTO testimonial, int index) {
        if (testimonial == null) {
            return null;
        }

        String quote = normalize(testimonial.quote());
        if (quote.isBlank()) {
            return null;
        }

        String author = normalize(testimonial.author());
        String role = normalize(testimonial.role());

        return new TestimonialDTO(
                quote,
                author.isBlank() ? "Aluno " + (index + 1) : author,
                role.isBlank() ? "Comunidade Judo Candoi" : role
        );
    }

    private String toSchedulesJson(List<ScheduleItemDTO> schedules) {
        try {
            return objectMapper.writeValueAsString(schedules);
        } catch (JsonProcessingException error) {
            throw new IllegalStateException("Nao foi possivel serializar os horarios de treino.", error);
        }
    }

    private String toGalleryJson(List<GalleryItemDTO> gallery) {
        try {
            return objectMapper.writeValueAsString(gallery);
        } catch (JsonProcessingException error) {
            throw new IllegalStateException("Nao foi possivel serializar a galeria dos atletas.", error);
        }
    }

    private String toTestimonialsJson(List<TestimonialDTO> testimonials) {
        try {
            return objectMapper.writeValueAsString(testimonials);
        } catch (JsonProcessingException error) {
            throw new IllegalStateException("Nao foi possivel serializar os depoimentos.", error);
        }
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }
}
