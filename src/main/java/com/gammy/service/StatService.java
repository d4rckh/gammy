package com.gammy.service;

import com.gammy.model.GameStatEntity;
import com.gammy.model.GameStatType;
import com.gammy.model.PlayerEntity;
import com.gammy.model.PlayerStatEntity;
import com.gammy.repository.GameStatRepository;
import com.gammy.repository.PlayerRepository;
import com.gammy.repository.PlayerStatRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
public class StatService {

    private final GameStatRepository gameStatRepository;
    private final PlayerStatRepository playerStatRepository;

    private final PlayerRepository playerRepository;

    public List<GameStatEntity> getGameStats() {
        return gameStatRepository.findAll();
    }

    public GameStatEntity createGameStat(GameStatEntity gameStat) {
        return gameStatRepository.save(gameStat);
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

    public PlayerStatEntity updatePlayerStat(Long playerId, String apiName, BigDecimal value) {
        Optional<PlayerStatEntity> optionalPlayerStatEntity = this.getPlayerStat(playerId, apiName);

        if (optionalPlayerStatEntity.isEmpty()) {
            throw new RuntimeException("Player stat not found");
        }

        PlayerStatEntity playerStatEntity = optionalPlayerStatEntity.get();

        BigDecimal oldValue = playerStatEntity.getValue();

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

        return playerStatRepository.update(playerStatEntity);
    }
}
