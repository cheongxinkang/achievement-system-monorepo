package com.xk.achievement.util;

import com.xk.achievement.dto.AchievementDTO;
import com.xk.achievement.model.Achievement;

public class AchievementMapper {

    private AchievementMapper() {
        // Utility class
    }

    public static AchievementDTO toDTO(Achievement achievement) {
        if (achievement == null) {
            return null;
        }
        AchievementDTO dto = new AchievementDTO();
        dto.setId(achievement.getId());
        dto.setAchievementName(achievement.getAchievementName());
        dto.setDescription(achievement.getDescription());
        dto.setListOfCriteria(achievement.getListOfCriteria());
        dto.setDeadline(achievement.getDeadline());
        dto.setCompleted(achievement.isCompleted());
        return dto;
    }

    public static Achievement toEntity(AchievementDTO dto) {
        if (dto == null) {
            return null;
        }
        Achievement achievement = new Achievement();
        achievement.setId(dto.getId());
        achievement.setAchievementName(dto.getAchievementName());
        achievement.setDescription(dto.getDescription());
        if (dto.getListOfCriteria() != null) {
            achievement.setListOfCriteria(new java.util.ArrayList<>(dto.getListOfCriteria()));
        }
        achievement.setDeadline(dto.getDeadline());
        achievement.setCompleted(dto.isCompleted());
        return achievement;
    }
}
