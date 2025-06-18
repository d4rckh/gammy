package com.gammy.model.dto;

import com.gammy.model.entity.achievement.GameAchievementEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Serdeable
@Data
@Builder
public class AchievementAnalytics {
    private GameAchievementEntity achievementEntity;
    private Long lastDays;
    private Map<String, Long> perDayUnlocks;
}
