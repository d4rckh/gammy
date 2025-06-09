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
    public PlayerInteractionEntity trackInteraction(
            @PathVariable Long playerId,
            @PathVariable String interactionApiName
    ) {
        authorizationService.canWritePlayerId(playerId);

        return interactionService.trackInteraction(playerId, interactionApiName);
    }

    @Get("player/{playerId}")
    @Secured("ROLE_ADMIN")
    public List<PlayerInteractionEntity> getPlayerInteractions(@PathVariable Long playerId) {
        return interactionService.getPlayerInteractions(playerId);
    }

    @Get("type/{apiName}")
    @Secured("ROLE_ADMIN")
    public List<PlayerInteractionEntity> getInteractionsByType(@PathVariable String apiName) {
        return interactionService.getInteractionsByApiName(apiName);
    }

    @Get("type")
    @Secured("ROLE_ADMIN")
    public List<GameInteractionEntity> getAllInteractionTypes() {
        return interactionService.getGameInteractions();
    }

    @Post("type")
    @Secured("ROLE_ADMIN")
    public GameInteractionEntity createInteractionType(@Body GameInteractionEntity gameInteractionEntity) {
        return interactionService.createGameInteractionType(gameInteractionEntity);
    }
}
