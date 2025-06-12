package com.gammy.controller;

import com.gammy.model.dto.PlayerGameStatUpdateRequest;
import com.gammy.model.entity.stat.GameStatEntity;
import com.gammy.model.entity.stat.PlayerStatEntity;
import com.gammy.model.entity.stat.StatUpdateHistoryEntity;
import com.gammy.service.AuthorizationService;
import com.gammy.service.StatService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller("stats")
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;
    private final AuthorizationService authorizationService;

    @Secured("ROLE_ADMIN")
    @Get
    public List<GameStatEntity> getGameStats() {
        return this.statService.getGameStats();
    }

    @Secured("ROLE_ADMIN")
    @Post
    public GameStatEntity createGameStat(@Body GameStatEntity gameStat) {
        return this.statService.createGameStat(gameStat);
    }

    @Secured("ROLE_ADMIN")
    @Put
    public GameStatEntity updateGameStat(@Body GameStatEntity gameStat) {
        return this.statService.updateGameStat(gameStat);
    }

    @Secured("ROLE_ADMIN")
    @Delete
    public void deleteGameStat(@Body GameStatEntity gameStat) {
        this.statService.deleteGameStat(gameStat);
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("player/{playerId}")
    public List<PlayerStatEntity> getPlayerStats(@PathVariable Long playerId) {
        authorizationService.throwIfCantReadPlayerId(playerId);

        return this.statService.getPlayerStats(playerId);
    }

    @Secured("ROLE_ADMIN")
    @Get("player/{playerId}/{statApiName}/history")
    public List<StatUpdateHistoryEntity> getPlayerStatHistory(@PathVariable Long playerId, @PathVariable String statApiName) {
        return this.statService.getPlayerStatHistory(playerId, statApiName);
    }

    @Secured("ROLE_ADMIN")
    @Get("player/{playerId}/history")
    public List<StatUpdateHistoryEntity> getPlayerStatHistory(@PathVariable Long playerId) {
        return this.statService.getPlayerStatHistory(playerId);
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post("player/{playerId}")
    public PlayerStatEntity updatePlayerStat(@PathVariable("playerId") Long playerId, @Body PlayerGameStatUpdateRequest playerGameStatUpdateRequest) {
        authorizationService.throwIfCantWritePlayerId(playerId);

        return this.statService.updatePlayerStat(
                playerId,
                playerGameStatUpdateRequest.getGameStatApiName(),
                playerGameStatUpdateRequest.getNewValue(),
                playerGameStatUpdateRequest.getUpdateMethod()
        );
    }
}
