package com.gammy.controller;

import com.gammy.model.dto.PlayerRegistrationRequest;
import com.gammy.model.entity.PlayerEntity;
import com.gammy.service.AuthorizationService;
import com.gammy.service.PlayerService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller("players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final AuthorizationService authorizationService;

    @Get
    @Secured("ROLE_ADMIN")
    public List<PlayerEntity> findAll() {
        return playerService.findAll();
    }

    @Get("{playerId}")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public PlayerEntity findById(@PathVariable Long playerId) {
        authorizationService.throwIfCantReadPlayerId(playerId);

        return playerService.findById(playerId).orElseThrow();
    }

    @Post
    @Secured(SecurityRule.IS_ANONYMOUS)
    public PlayerEntity createPlayer(@Body PlayerRegistrationRequest playerRegistrationRequest) {
        return this.playerService.createPlayer(playerRegistrationRequest);
    }

    @Put
    @Secured("ROLE_ADMIN")
    public PlayerEntity updatePlayer(@Body PlayerEntity playerEntity) {
        return this.playerService.updatePlayer(playerEntity);
    }
}
