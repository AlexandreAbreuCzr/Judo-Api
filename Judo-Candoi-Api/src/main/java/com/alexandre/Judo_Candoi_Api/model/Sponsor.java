package com.alexandre.Judo_Candoi_Api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sponsors")
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String logoUrl;

    @Column(columnDefinition = "TEXT")
    private String websiteUrl;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private int displayOrder = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected Sponsor() {
    }

    public Sponsor(
            String name,
            String description,
            String logoUrl,
            String websiteUrl,
            boolean active,
            int displayOrder
    ) {
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.websiteUrl = websiteUrl;
        this.active = active;
        this.displayOrder = displayOrder;
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public boolean isActive() {
        return active;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void update(
            String name,
            String description,
            String logoUrl,
            String websiteUrl,
            boolean active,
            int displayOrder
    ) {
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.websiteUrl = websiteUrl;
        this.active = active;
        this.displayOrder = displayOrder;
    }
}
