package com.xk.achievement.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AchievementDTO {
    private Long id;
    private String achievementName;
    private String description;
    private List<String> listOfCriteria = new ArrayList<>();
    private LocalDate deadline;
    private boolean completed;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getListOfCriteria() {
        return listOfCriteria;
    }

    public void setListOfCriteria(List<String> listOfCriteria) {
        this.listOfCriteria = listOfCriteria;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
