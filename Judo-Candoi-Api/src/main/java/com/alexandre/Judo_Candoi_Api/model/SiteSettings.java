package com.alexandre.Judo_Candoi_Api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "site_settings")
public class SiteSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String brandName;

    @Column(nullable = false, length = 200)
    private String heroTitle;

    @Column(nullable = false, length = 500)
    private String heroSubtitle;

    @Column(nullable = false, length = 260)
    private String impactPhrase;

    @Column(nullable = false, length = 80)
    private String callToActionPrimaryLabel;

    @Column(nullable = false, length = 180)
    private String callToActionPrimaryUrl;

    @Column(nullable = false, length = 80)
    private String callToActionSecondaryLabel;

    @Column(nullable = false, length = 120)
    private String aboutTitle;

    @Column(nullable = false, length = 1200)
    private String aboutStory;

    @Column(nullable = false, length = 400)
    private String aboutHighlight;

    @Column(nullable = false, length = 120)
    private String trialTitle;

    @Column(nullable = false, length = 500)
    private String trialDescription;

    @Column(nullable = false, length = 500)
    private String finalCallToAction;

    @Column(nullable = false, length = 20)
    private String counterStudents;

    @Column(nullable = false, length = 20)
    private String counterMedals;

    @Column(nullable = false, length = 20)
    private String counterYears;

    @Column
    private Integer medalCompetitions;

    @Column
    private Integer medalFights;

    @Column
    private Integer medalGold;

    @Column
    private Integer medalSilver;

    @Column
    private Integer medalBronze;

    @Column(nullable = false, length = 30)
    private String whatsappNumber;

    @Column(nullable = false, length = 80)
    private String instagramHandle;

    @Column(nullable = false, length = 220)
    private String academyAddress;

    @Column(nullable = false, length = 600)
    private String googleMapsEmbed;

    @Column(length = 4000)
    private String schedulesJson;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected SiteSettings() {
    }

    public SiteSettings(
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
            Integer medalCompetitions,
            Integer medalFights,
            Integer medalGold,
            Integer medalSilver,
            Integer medalBronze,
            String whatsappNumber,
            String instagramHandle,
            String academyAddress,
            String googleMapsEmbed,
            String schedulesJson
    ) {
        this.brandName = brandName;
        this.heroTitle = heroTitle;
        this.heroSubtitle = heroSubtitle;
        this.impactPhrase = impactPhrase;
        this.callToActionPrimaryLabel = callToActionPrimaryLabel;
        this.callToActionPrimaryUrl = callToActionPrimaryUrl;
        this.callToActionSecondaryLabel = callToActionSecondaryLabel;
        this.aboutTitle = aboutTitle;
        this.aboutStory = aboutStory;
        this.aboutHighlight = aboutHighlight;
        this.trialTitle = trialTitle;
        this.trialDescription = trialDescription;
        this.finalCallToAction = finalCallToAction;
        this.counterStudents = counterStudents;
        this.counterMedals = counterMedals;
        this.counterYears = counterYears;
        this.medalCompetitions = medalCompetitions;
        this.medalFights = medalFights;
        this.medalGold = medalGold;
        this.medalSilver = medalSilver;
        this.medalBronze = medalBronze;
        this.whatsappNumber = whatsappNumber;
        this.instagramHandle = instagramHandle;
        this.academyAddress = academyAddress;
        this.googleMapsEmbed = googleMapsEmbed;
        this.schedulesJson = schedulesJson;
    }

    @PrePersist
    private void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getHeroTitle() {
        return heroTitle;
    }

    public String getHeroSubtitle() {
        return heroSubtitle;
    }

    public String getImpactPhrase() {
        return impactPhrase;
    }

    public String getCallToActionPrimaryLabel() {
        return callToActionPrimaryLabel;
    }

    public String getCallToActionPrimaryUrl() {
        return callToActionPrimaryUrl;
    }

    public String getCallToActionSecondaryLabel() {
        return callToActionSecondaryLabel;
    }

    public String getAboutTitle() {
        return aboutTitle;
    }

    public String getAboutStory() {
        return aboutStory;
    }

    public String getAboutHighlight() {
        return aboutHighlight;
    }

    public String getTrialTitle() {
        return trialTitle;
    }

    public String getTrialDescription() {
        return trialDescription;
    }

    public String getFinalCallToAction() {
        return finalCallToAction;
    }

    public String getCounterStudents() {
        return counterStudents;
    }

    public String getCounterMedals() {
        return counterMedals;
    }

    public String getCounterYears() {
        return counterYears;
    }

    public int getMedalCompetitions() {
        return medalCompetitions == null ? 36 : medalCompetitions;
    }

    public int getMedalFights() {
        return medalFights == null ? 478 : medalFights;
    }

    public int getMedalGold() {
        return medalGold == null ? 21 : medalGold;
    }

    public int getMedalSilver() {
        return medalSilver == null ? 24 : medalSilver;
    }

    public int getMedalBronze() {
        return medalBronze == null ? 42 : medalBronze;
    }

    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public String getInstagramHandle() {
        return instagramHandle;
    }

    public String getAcademyAddress() {
        return academyAddress;
    }

    public String getGoogleMapsEmbed() {
        return googleMapsEmbed;
    }

    public String getSchedulesJson() {
        return schedulesJson;
    }

    public void update(
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
            Integer medalCompetitions,
            Integer medalFights,
            Integer medalGold,
            Integer medalSilver,
            Integer medalBronze,
            String whatsappNumber,
            String instagramHandle,
            String academyAddress,
            String googleMapsEmbed,
            String schedulesJson
    ) {
        this.brandName = brandName;
        this.heroTitle = heroTitle;
        this.heroSubtitle = heroSubtitle;
        this.impactPhrase = impactPhrase;
        this.callToActionPrimaryLabel = callToActionPrimaryLabel;
        this.callToActionPrimaryUrl = callToActionPrimaryUrl;
        this.callToActionSecondaryLabel = callToActionSecondaryLabel;
        this.aboutTitle = aboutTitle;
        this.aboutStory = aboutStory;
        this.aboutHighlight = aboutHighlight;
        this.trialTitle = trialTitle;
        this.trialDescription = trialDescription;
        this.finalCallToAction = finalCallToAction;
        this.counterStudents = counterStudents;
        this.counterMedals = counterMedals;
        this.counterYears = counterYears;
        this.medalCompetitions = medalCompetitions;
        this.medalFights = medalFights;
        this.medalGold = medalGold;
        this.medalSilver = medalSilver;
        this.medalBronze = medalBronze;
        this.whatsappNumber = whatsappNumber;
        this.instagramHandle = instagramHandle;
        this.academyAddress = academyAddress;
        this.googleMapsEmbed = googleMapsEmbed;
        this.schedulesJson = schedulesJson;
    }
}
