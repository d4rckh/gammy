package com.gammy.service;

import com.gammy.model.dto.PlayerGameStatUpdateMethod;
import com.gammy.model.entity.PlayerEntity;
import com.gammy.model.entity.leaderboard.LeaderboardSortMethod;
import com.gammy.model.entity.stat.GameStatEntity;
import com.gammy.model.entity.stat.GameStatType;
import com.gammy.model.entity.stat.PlayerStatEntity;
import com.gammy.model.entity.stat.StatUpdateHistoryEntity;
import com.gammy.repository.PlayerRepository;
import com.gammy.repository.stat.GameStatRepository;
import com.gammy.repository.stat.PlayerStatRepository;
import com.gammy.repository.stat.StatUpdateHistoryRepository;
import io.micronaut.data.model.Sort;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
@RequiredArgsConstructor
public class StatService {

    private final GameStatRepository gameStatRepository;
    private final PlayerStatRepository playerStatRepository;

    private final PlayerRepository playerRepository;
    private final StatUpdateHistoryRepository statUpdateHistoryRepository;

    public Stream<PlayerStatEntity> streamByGameStatApiName(String apiName) {
        return this.playerStatRepository.streamByGameStatApiName(apiName);
    }

    public List<PlayerStatEntity> getOrderedGameStatScores(String apiName, LeaderboardSortMethod sortMethod) {
        Sort sort = switch (sortMethod) {
            case ASCENDING -> Sort.of(Sort.Order.asc("value"));
            case DESCENDING -> Sort.of(Sort.Order.desc("value"));
        };

        return this.playerStatRepository.findByGameStatApiName(apiName, sort);
    }

    public Optional<GameStatEntity> findByApiName(String apiName) {
        return gameStatRepository.findByApiName(apiName);
    }

    public List<GameStatEntity> getGameStats() {
        return gameStatRepository.findAll();
    }

    public GameStatEntity createGameStat(GameStatEntity gameStat) {
        return gameStatRepository.save(gameStat);
    }

    public void deleteGameStat(GameStatEntity gameStat) {
        gameStatRepository.delete(gameStat);
    }

    public Optional<PlayerStatEntity> getPlayerStat(Long playerId, String apiName) {
        Optional<GameStatEntity> gameStatEntity = gameStatRepository.findByApiName(apiName);

        Optional<PlayerEntity> playerEntity = playerRepository.findById(playerId);

        if (playerEntity.isEmpty()) {
            return Optional.empty();
        }

        if (gameStatEntity.isEmpty()) {
            return Optional.empty();
        }

        Optional<PlayerStatEntity> playerStatEntity = playerStatRepository.findByPlayerIdAndGameStatApiName(playerId, apiName);

        if (playerStatEntity.isEmpty()) {
            PlayerStatEntity playerStat = new PlayerStatEntity();

            playerStat.setGameStat(gameStatEntity.get());
            playerStat.setPlayer(playerEntity.get());

            playerStat.setValue(gameStatEntity.get().getDefaultValue());

            return Optional.of(playerStatRepository.save(playerStat));
        }

        return playerStatEntity;
    }

    public List<PlayerStatEntity> getPlayerStats(Long playerId) {
        return this.getGameStats()
                .stream()
                .map(gameStatEntity -> this.getPlayerStat(playerId, gameStatEntity.getApiName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public PlayerStatEntity updatePlayerStat(Long playerId, String apiName, BigDecimal value, PlayerGameStatUpdateMethod updateMethod) {
        Optional<PlayerStatEntity> optionalPlayerStatEntity = this.getPlayerStat(playerId, apiName);

        if (optionalPlayerStatEntity.isEmpty()) {
            throw new RuntimeException("Player stat not found");
        }

        PlayerStatEntity playerStatEntity = optionalPlayerStatEntity.get();

        BigDecimal oldValue = playerStatEntity.getValue();

        switch (updateMethod) {
            case PlayerGameStatUpdateMethod.LARGEST -> value = value.max(oldValue);
            case PlayerGameStatUpdateMethod.LOWEST -> value = value.min(oldValue);
            // case FORCE -> keep the value
        }

        BigDecimal minValue = playerStatEntity.getGameStat().getMinValue();
        BigDecimal maxValue = playerStatEntity.getGameStat().getMaxValue();
        BigDecimal maxInterval = playerStatEntity.getGameStat().getMaxChange();
        Boolean onlyIncrement = playerStatEntity.getGameStat().getOnlyIncrement();

        GameStatType gameStatType = playerStatEntity.getGameStat().getType();

        if (gameStatType.equals(GameStatType.INTEGER)) {
            value = value.setScale(0, RoundingMode.FLOOR);
        }

        if (
                onlyIncrement &&
                        oldValue.compareTo(value) > 0
        ) {
            throw new RuntimeException("Value must not be incremented");
        }

        if (
                !Objects.isNull(minValue) &&
                        minValue.compareTo(value) > 0
        ) {
            throw new RuntimeException("Value smaller than min value");
        }

        if (
                !Objects.isNull(maxValue) &&
                        maxValue.compareTo(value) < 0
        ) {
            throw new RuntimeException("Value larger than max value");
        }

        if (
                !Objects.isNull(maxInterval) &&
                        oldValue.subtract(value).abs().compareTo(maxInterval) > 0
        ) {
            throw new RuntimeException("Value changed more than allowed interval");
        }

        playerStatEntity.setValue(value);

        statUpdateHistoryRepository.save(
                StatUpdateHistoryEntity.builder()
                        .gameStat(playerStatEntity.getGameStat())
                        .player(playerStatEntity.getPlayer())
                        .newValue(value)
                        .suspicious(false)
                        .oldValue(oldValue).build()
        );

        return playerStatRepository.update(playerStatEntity);
    }

    public List<StatUpdateHistoryEntity> getPlayerStatHistory(Long playerId, String statApiName) {
        return this.statUpdateHistoryRepository.findByPlayerIdAndGameStatApiName(playerId, statApiName);
    }

    public List<StatUpdateHistoryEntity> getPlayerStatHistory(Long playerId) {
        return this.statUpdateHistoryRepository.findByPlayerId(playerId);
    }

    public GameStatEntity updateGameStat(GameStatEntity gameStat) {
        return this.gameStatRepository.update(gameStat);
    }
}
