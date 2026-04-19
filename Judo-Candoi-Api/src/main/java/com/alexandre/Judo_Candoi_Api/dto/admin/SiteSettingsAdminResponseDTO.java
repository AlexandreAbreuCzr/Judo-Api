package com.alexandre.Judo_Candoi_Api.dto.admin;

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
        String googleMapsEmbed
) {
}
