package com.gammy.model.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
@Serdeable
public class GameAnalytics {

    private long totalPlayers;
    private long totalAchievements;
    private long totalReports;

    private Map<String, Long> interactionsCount;
    private Map<String, Long> achievementsUnlockCount;

}
