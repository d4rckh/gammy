package com.gammy.service;

import com.gammy.model.entity.PlayerEntity;
import com.gammy.model.entity.interaction.GameInteractionEntity;
import com.gammy.model.entity.interaction.GameInteractionStreak;
import com.gammy.model.entity.interaction.PlayerInteractionEntity;
import com.gammy.repository.interaction.GameInteractionRepository;
import com.gammy.repository.interaction.PlayerInteractionRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
public class InteractionService {

    private final GameInteractionRepository gameInteractionRepository;
    private final PlayerInteractionRepository playerInteractionRepository;

    private final PlayerService playerService;

    public PlayerInteractionEntity trackInteraction(Long playerId, String interactionApiName) {
        GameInteractionEntity gameInteractionEntity = gameInteractionRepository.findByApiName(interactionApiName).orElseThrow();
        PlayerEntity playerEntity = playerService.findById(playerId).orElseThrow();

        PlayerInteractionEntity interactionEntity = new PlayerInteractionEntity();
        interactionEntity.setGameInteraction(gameInteractionEntity);
        interactionEntity.setPlayer(playerEntity);

        if (gameInteractionEntity.getStreak() == GameInteractionStreak.DAILY) {
            Optional<PlayerInteractionEntity> lastPlayerInteractionOptional =
                    playerInteractionRepository.findLastByPlayerIdAndGameInteraction_ApiNameOrderByTimestampDesc(playerId, interactionApiName);

            if (lastPlayerInteractionOptional.isPresent()) {
                PlayerInteractionEntity lastPlayerInteraction = lastPlayerInteractionOptional.get();

                ZoneId zone = ZoneId.systemDefault();
                LocalDate lastDate = lastPlayerInteraction.getTimestamp().atZone(zone).toLocalDate();
                LocalDate today = LocalDate.now(zone);

                if (lastDate.equals(today.minusDays(1))) {
                    interactionEntity.setStreak(lastPlayerInteraction.getStreak() + 1);
                } else if (lastDate.equals(today)) {
                    return lastPlayerInteraction;
                } else {
                    interactionEntity.setStreak(1L);
                }
            } else {
                interactionEntity.setStreak(1L);
            }
        }

        return playerInteractionRepository.save(interactionEntity);
    }

    public List<PlayerInteractionEntity> getPlayerInteractions(Long playerId) {
        return this.playerInteractionRepository.findAllByPlayerId(playerId);
    }

    public List<PlayerInteractionEntity> getInteractionsByApiName(String apiName) {
        return this.playerInteractionRepository.findAllByGameInteractionApiName(apiName);
    }

    public List<GameInteractionEntity> getGameInteractions() {
        return this.gameInteractionRepository.findAll();
    }

    public GameInteractionEntity createGameInteractionType(GameInteractionEntity gameInteractionEntity) {
        return this.gameInteractionRepository.save(gameInteractionEntity);
    }

    public GameInteractionEntity updateGameInteractionType(GameInteractionEntity gameInteractionEntity) {
        return this.gameInteractionRepository.update(gameInteractionEntity);
    }
}
