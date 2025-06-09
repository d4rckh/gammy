package com.gammy.service;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class PlayerStatsHelper {

    private final AchievementService achievementService;
    private final Long playerId;
    private final String currentAchievementApiName;
    private final StatService statService;

    public BigDecimal stat(String statName) {
        return statService.getPlayerStat(playerId, statName)
                .orElseThrow(() -> new RuntimeException("Stat not found"))
                .getValue();
    }

    public boolean hasUnlocked(String achId) {
        if (achId.equals(currentAchievementApiName)) return false;
        return achievementService.getPlayerAchievement(playerId, achId).isPresent();
    }
}

