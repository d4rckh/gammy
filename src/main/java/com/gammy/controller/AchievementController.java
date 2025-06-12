package com.gammy.controller;

import com.gammy.model.entity.achievement.GameAchievementEntity;
import com.gammy.model.entity.achievement.PlayerAchievementEntity;
import com.gammy.service.AchievementService;
import com.gammy.service.AuthorizationService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Controller("achievements")
@RequiredArgsConstructor
public class AchievementController {
    private final AchievementService achievementService;
    private final AuthorizationService authorizationService;

    @Get
    @Secured(SecurityRule.IS_AUTHENTICATED)
    List<GameAchievementEntity> getGameAchievements() {
        return this.achievementService.getGameAchievements();
    }

    @Post
    @Secured("ROLE_ADMIN")
    GameAchievementEntity createAchievement(@Body GameAchievementEntity achievement) {
        return this.achievementService.createAchievement(achievement);
    }

    @Put
    @Secured("ROLE_ADMIN")
    GameAchievementEntity updateAchievement(@Body GameAchievementEntity achievement) {
        return this.achievementService.updateAchievement(achievement);
    }

    @Delete
    @Secured("ROLE_ADMIN")
    void deleteAchievement(@Body GameAchievementEntity achievement) {
        this.achievementService.deleteAchievement(achievement);
    }

    @Get("player/{playerId}")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    Map<String, Boolean> getAchievementStatusForPlayer(@PathVariable Long playerId) {
        authorizationService.throwIfCantReadPlayerId(playerId);

        return this.achievementService.getAchievementStatusForPlayer(playerId);
    }

    @Post("player/{playerId}/{apiName}")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    PlayerAchievementEntity unlockPlayerAchievement(@PathVariable Long playerId, @PathVariable String apiName) {
        authorizationService.throwIfCantWritePlayerId(playerId);

        return this.achievementService.unlockPlayerAchievement(playerId, apiName);
    }

    @Delete("player/{playerId}/{apiName}")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    void removePlayerAchievement(@PathVariable Long playerId, @PathVariable String apiName) {
        authorizationService.throwIfCantWritePlayerId(playerId);

        this.achievementService.removePlayerAchievement(playerId, apiName);
    }
}
