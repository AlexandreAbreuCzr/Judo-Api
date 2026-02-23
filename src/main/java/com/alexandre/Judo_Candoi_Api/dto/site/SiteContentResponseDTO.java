package com.alexandre.Judo_Candoi_Api.dto.site;

import java.util.List;

public record SiteContentResponseDTO(
        String brandName,
        String heroTitle,
        String heroSubtitle,
        String impactPhrase,
        String callToActionPrimaryLabel,
        String callToActionPrimaryUrl,
        String callToActionSecondaryLabel,
        String callToActionSecondaryUrl,
        String aboutTitle,
        String aboutStory,
        String aboutHighlight,
        List<CounterDTO> counters,
        MedalStatsDTO medalStats,
        List<ProgramDTO> programs,
        List<String> methodology,
        List<AchievementDTO> achievements,
        List<GalleryItemDTO> gallery,
        List<TestimonialDTO> testimonials,
        String trialTitle,
        String trialDescription,
        List<ScheduleItemDTO> schedules,
        List<BlogPostDTO> blogPosts,
        List<SponsorDTO> sponsors,
        List<TimelineStepDTO> timeline,
        List<PrideStudentDTO> prideStudents,
        ContactDTO contact,
        String finalCallToAction
) {
}
