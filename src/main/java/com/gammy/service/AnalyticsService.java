package com.gammy.service;

import com.gammy.model.ICountableInTime;
import com.gammy.model.dto.AchievementAnalytics;
import com.gammy.model.dto.GameAnalytics;
import com.gammy.model.dto.StatAnalytics;
import com.gammy.model.entity.achievement.PlayerAchievementEntity;
import com.gammy.model.entity.stat.GameStatEntity;
import com.gammy.model.entity.stat.PlayerStatEntity;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.annotation.Nullable;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
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

    public AchievementAnalytics getAchievementAnalytics(String apiName, @Nullable Long lastDays) {
        long effectiveDays = lastDays != null ? lastDays : 30;
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(effectiveDays);

        List<PlayerAchievementEntity> achievements = achievementService.getPlayerAchievements(null, apiName, effectiveDays);

        return AchievementAnalytics.builder()
                .achievementEntity(achievementService.getAchievementByApiName(apiName).orElseThrow())
                .lastDays(effectiveDays)
                .perDayUnlocks(countInTime(achievements, startDate, endDate))
                .build();
    }

    private <T extends ICountableInTime> Map<String, Long> countInTime(List<T> items, LocalDate startDate, LocalDate endDate) {
        Map<String, Long> dailyCounts = new LinkedHashMap<>();

        if (items.isEmpty()) {
            return dailyCounts;
        }

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            LocalDate currentDate = date;

            long count = items.stream()
                    .map(ICountableInTime::getTimestamp)
                    .filter(Objects::nonNull)
                    .map(ts -> ts.atZone(ZoneId.of("UTC")).toLocalDate())
                    .filter(localDate -> localDate.equals(currentDate))
                    .count();

            dailyCounts.put(date.toString(), count);
        }

        return dailyCounts;
    }


}
