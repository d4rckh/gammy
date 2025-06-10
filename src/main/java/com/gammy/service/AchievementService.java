package com.gammy.service;

import com.gammy.model.entity.PlayerEntity;
import com.gammy.model.entity.achievement.GameAchievementEntity;
import com.gammy.model.entity.achievement.PlayerAchievementEntity;
import com.gammy.repository.achievement.GameAchievementRepository;
import com.gammy.repository.achievement.PlayerAchievementRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class AchievementService {
    private final GameAchievementRepository gameAchievementRepository;
    private final PlayerAchievementRepository playerAchievementRepository;

    private final PlayerService playerService;
    private final StatService statService;

    public GameAchievementEntity createAchievement(GameAchievementEntity gameAchievementEntity) {
        return gameAchievementRepository.save(gameAchievementEntity);
    }

    public List<GameAchievementEntity> getAllAchievements() {
        return gameAchievementRepository.findAll();
    }

    public Map<String, Boolean> getAchievementsByPlayer(Long playerId) {
        return this.getAllAchievements().stream()
                .collect(Collectors.toMap(
                        GameAchievementEntity::getApiName,
                        achievement ->
                                this.getPlayerAchievement(playerId, achievement.getApiName()).isPresent()
                ));
    }

    private boolean executeAchievementCondition(String expression, Long playerId, String achievementApiName) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        PlayerStatsHelper playerStatsHelper = new PlayerStatsHelper(this, playerId, achievementApiName, statService);

        context.setVariable("helper", playerStatsHelper);
        context.setRootObject(playerStatsHelper);

        Boolean result = parser.parseExpression(expression).getValue(context, Boolean.class);
        return Boolean.TRUE.equals(result);
    }

    public Optional<PlayerAchievementEntity> getPlayerAchievement(Long playerId, String achievementApiName) {
        Optional<PlayerAchievementEntity> playerAchievementEntityOptional = playerAchievementRepository.findByPlayerIdAndGameAchievementApiName(playerId, achievementApiName);

        GameAchievementEntity gameAchievement = gameAchievementRepository.findByApiName(achievementApiName).orElseThrow();

        if (playerAchievementEntityOptional.isPresent()) {
            return playerAchievementEntityOptional;
        }

        if (
                !Objects.isNull(gameAchievement.getUnlockExpression()) &&
                        this.executeAchievementCondition(gameAchievement.getUnlockExpression(), playerId, achievementApiName)) {
            return Optional.ofNullable(this.unlockPlayerAchievement(playerId, achievementApiName));
        }

        return Optional.empty();
    }

    public PlayerAchievementEntity unlockPlayerAchievement(Long playerId, String achievementApiName) {
        GameAchievementEntity gameAchievement = gameAchievementRepository.findByApiName(achievementApiName).orElseThrow();
        PlayerEntity player = playerService.findById(playerId).orElseThrow();

        if (this.playerAchievementRepository.findByPlayerIdAndGameAchievementApiName(playerId, achievementApiName).isPresent()) {
            throw new RuntimeException("Achievement already unlocked");
        }

        PlayerAchievementEntity playerAchievementEntity = new PlayerAchievementEntity();
        playerAchievementEntity.setGameAchievement(gameAchievement);
        playerAchievementEntity.setPlayer(player);

        return playerAchievementRepository.save(playerAchievementEntity);
    }

    public void removePlayerAchievement(Long playerId, String achievementApiName) {
        playerAchievementRepository.delete(
                this.getPlayerAchievement(playerId, achievementApiName).orElseThrow()
        );
    }

    public GameAchievementEntity updateAchievement(GameAchievementEntity achievement) {
        return this.gameAchievementRepository.update(achievement);
    }
}
