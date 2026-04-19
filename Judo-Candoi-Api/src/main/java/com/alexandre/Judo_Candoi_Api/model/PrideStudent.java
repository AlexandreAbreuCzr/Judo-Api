package com.alexandre.Judo_Candoi_Api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pride_students")
public class PrideStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 260)
    private String achievement;

    @Column(name = "month_label", nullable = false, length = 40)
    private String month;

    @Column(length = 500)
    private String imageUrl;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private int displayOrder = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected PrideStudent() {
    }

    public PrideStudent(String name, String achievement, String month, String imageUrl, boolean active, int displayOrder) {
        this.name = name;
        this.achievement = achievement;
        this.month = month;
        this.imageUrl = imageUrl;
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

    public String getAchievement() {
        return achievement;
    }

    public String getMonth() {
        return month;
    }

    public boolean isActive() {
        return active;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void update(String name, String achievement, String month, String imageUrl, boolean active, int displayOrder) {
        this.name = name;
        this.achievement = achievement;
        this.month = month;
        this.imageUrl = imageUrl;
        this.active = active;
        this.displayOrder = displayOrder;
    }
}
