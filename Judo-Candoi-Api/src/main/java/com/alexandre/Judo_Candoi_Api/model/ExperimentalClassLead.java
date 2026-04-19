package com.alexandre.Judo_Candoi_Api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "experimental_class_leads")
public class ExperimentalClassLead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 250)
    private String objective;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected ExperimentalClassLead() {
    }

    public ExperimentalClassLead(String name, Integer age, String phone, String objective) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.objective = objective;
    }

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getObjective() {
        return objective;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
