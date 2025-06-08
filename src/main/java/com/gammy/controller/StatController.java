package com.gammy.controller;

import com.gammy.model.GameStatEntity;
import com.gammy.model.PlayerStatEntity;
import com.gammy.model.dto.PlayerGameStatUpdateRequest;
import com.gammy.service.PlayerService;
import com.gammy.service.StatService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.metrics.Stat;

import java.util.List;

@Controller("/stats")
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;

    @Secured("ROLE_ADMIN")
    @Get("game")
    public List<GameStatEntity> getGameStats() {
        return this.statService.getGameStats();
    }

    @Secured("ROLE_ADMIN")
    @Post("game")
    public GameStatEntity addGameStat(@Body GameStatEntity gameStat) {
        return this.statService.createGameStat(gameStat);
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("player/{playerId}")
    public List<PlayerStatEntity> getPlayerStats(@PathVariable Long playerId) {
        return this.statService.getPlayerStats(playerId);
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post("player/{playerId}")
    public PlayerStatEntity updatePlayerStat(@PathVariable("playerId") Long playerId, @Body PlayerGameStatUpdateRequest playerGameStatUpdateRequest) {
        return this.statService.updatePlayerStat(
                playerId,
                playerGameStatUpdateRequest.getGameStatApiName(),
                playerGameStatUpdateRequest.getNewValue()
        );
    }
}
