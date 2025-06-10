package com.gammy.controller;

import com.gammy.model.entity.interaction.GameInteractionEntity;
import com.gammy.model.entity.interaction.PlayerInteractionEntity;
import com.gammy.service.AuthorizationService;
import com.gammy.service.InteractionService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller("/interactions")
@RequiredArgsConstructor
public class InteractionController {
    private final InteractionService interactionService;
    private final AuthorizationService authorizationService;

    @Post("track/{playerId}/{interactionApiName}")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    PlayerInteractionEntity trackInteraction(
            @PathVariable Long playerId,
            @PathVariable String interactionApiName
    ) {
        authorizationService.canWritePlayerId(playerId);

        return interactionService.trackInteraction(playerId, interactionApiName);
    }

    @Get("player/{playerId}")
    @Secured("ROLE_ADMIN")
    List<PlayerInteractionEntity> getPlayerInteractions(@PathVariable Long playerId) {
        return interactionService.getPlayerInteractions(playerId);
    }

    @Get("type/{apiName}")
    @Secured("ROLE_ADMIN")
    List<PlayerInteractionEntity> getInteractionsByApiName(@PathVariable String apiName) {
        return interactionService.getInteractionsByApiName(apiName);
    }

    @Get("type")
    @Secured("ROLE_ADMIN")
    List<GameInteractionEntity> getGameInteractions() {
        return interactionService.getGameInteractions();
    }

    @Post("type")
    @Secured("ROLE_ADMIN")
    GameInteractionEntity createInteraction(@Body GameInteractionEntity gameInteractionEntity) {
        return interactionService.createGameInteraction(gameInteractionEntity);
    }

    @Put("type")
    @Secured("ROLE_ADMIN")
    GameInteractionEntity updateGameInteraction(@Body GameInteractionEntity gameInteractionEntity) {
        return interactionService.updateGameInteraction(gameInteractionEntity);
    }
}
