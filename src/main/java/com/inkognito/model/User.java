package com.inkognito.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username is required")
    private String username;
    
    @Column(name = "professional_job")
    private String professionalJob;
    
    @Column(name = "hobby_job")
    private String hobbyJob;
    
    @Column(name = "daily_feeling")
    private String dailyFeeling;
    
    @Column(name = "life_situation")
    private String lifeSituation;
    
    @Column(name = "feeling_updated_at")
    private LocalDateTime feelingUpdatedAt;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public User() {}
    
    public User(String username) {
        this.username = username;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getProfessionalJob() {
        return professionalJob;
    }
    
    public void setProfessionalJob(String professionalJob) {
        this.professionalJob = professionalJob;
    }
    
    public String getHobbyJob() {
        return hobbyJob;
    }
    
    public void setHobbyJob(String hobbyJob) {
        this.hobbyJob = hobbyJob;
    }
    
    public String getDailyFeeling() {
        return dailyFeeling;
    }
    
    public void setDailyFeeling(String dailyFeeling) {
        this.dailyFeeling = dailyFeeling;
        this.feelingUpdatedAt = LocalDateTime.now();
    }
    
    public String getLifeSituation() {
        return lifeSituation;
    }
    
    public void setLifeSituation(String lifeSituation) {
        this.lifeSituation = lifeSituation;
    }
    
    public LocalDateTime getFeelingUpdatedAt() {
        return feelingUpdatedAt;
    }
    
    public void setFeelingUpdatedAt(LocalDateTime feelingUpdatedAt) {
        this.feelingUpdatedAt = feelingUpdatedAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}