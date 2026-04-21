package com.alexandre.Judo_Candoi_Api.dto.admin;

import com.alexandre.Judo_Candoi_Api.dto.site.GalleryItemDTO;
import com.alexandre.Judo_Candoi_Api.dto.site.ScheduleItemDTO;
import com.alexandre.Judo_Candoi_Api.dto.site.TestimonialDTO;

import java.util.List;

public record SiteSettingsAdminResponseDTO(
        Long id,
        String brandName,
        String heroTitle,
        String heroSubtitle,
        String impactPhrase,
        String callToActionPrimaryLabel,
        String callToActionPrimaryUrl,
        String callToActionSecondaryLabel,
        String aboutTitle,
        String aboutStory,
        String aboutHighlight,
        String trialTitle,
        String trialDescription,
        String finalCallToAction,
        String counterStudents,
        String counterMedals,
        String counterYears,
        int medalCompetitions,
        int medalFights,
        int medalGold,
        int medalSilver,
        int medalBronze,
        String whatsappNumber,
        String instagramHandle,
        String academyAddress,
        String googleMapsEmbed,
        List<ScheduleItemDTO> schedules,
        List<GalleryItemDTO> gallery,
        List<TestimonialDTO> testimonials
) {
}
