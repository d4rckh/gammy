package com.gammy.service;

import com.gammy.model.dto.GameAnalytics;
import com.gammy.model.dto.StatAnalytics;
import com.gammy.model.entity.stat.GameStatEntity;
import com.gammy.model.entity.stat.PlayerStatEntity;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
@RequiredArgsConstructor
public class AnalyticsService {

    private final PlayerService playerService;
    private final AchievementService achievementService;
    private final InteractionService interactionService;
    private final PlayerReportService reportService;
    private final StatService statService;

    public GameAnalytics getGameAnalytics() {
        return GameAnalytics.builder()
                .totalReports(reportService.getReportsCount())
                .totalPlayers(playerService.getPlayerCount())
                .interactionsCount(interactionService.getInteractionsCountByApiName())
                .totalAchievements(achievementService.getAchievementsCount())
                .achievementsUnlockCount(achievementService.getAchievementsUnlockCount())
                .build();
    }

    @Transactional
    public StatAnalytics getStatAnalytics(String apiName) {
        GameStatEntity stat = statService.findByApiName(apiName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid stat name"));

        BigDecimal defaultValue = stat.getDefaultValue();

        try (Stream<PlayerStatEntity> s = statService.streamByGameStatApiName(apiName)) {
            List<BigDecimal> values = s
                    .map(PlayerStatEntity::getValue)
                    .filter(Objects::nonNull)
                    .toList();

            if (values.isEmpty()) {
                return StatAnalytics.builder().build();
            }

            int count = values.size();
            BigDecimal sum = values.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            float avg = sum.divide(BigDecimal.valueOf(count), 4, RoundingMode.HALF_UP)
                    .floatValue();

            long totalNonDefault = values.stream()
                    .filter(v -> v.compareTo(defaultValue) != 0)
                    .count();

            Map<Float, Long> dist = values.stream()
                    .map(v -> v.setScale(1, RoundingMode.HALF_UP).floatValue())
                    .collect(Collectors.groupingBy(f -> f, Collectors.counting()));

            BigDecimal mostCommon = dist.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(e -> BigDecimal.valueOf(e.getKey()))
                    .orElse(null);

            double variance = values.stream()
                    .mapToDouble(v -> Math.pow(v.floatValue() - avg, 2))
                    .average()
                    .orElse(0);
            float stdDev = (float) Math.sqrt(variance);

            long above = values.stream().filter(v -> v.floatValue() > avg).count();
            float pctAbove = (above * 100f) / count;

            return StatAnalytics.builder()
                    .totalNonDefaultEntries(totalNonDefault)
                    .averageValue(avg)
                    .standardDeviation(stdDev)
                    .distributionOfValues(dist)
                    .mostCommonValue(mostCommon)
                    .percentAboveAverage(pctAbove)
                    .build();
        }
    }

}
