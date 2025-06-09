package com.gammy.controller;

import com.gammy.service.PlayerService;
import com.gammy.model.entity.PlayerEntity;
import com.gammy.model.dto.PlayerRegistrationRequest;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller("players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @Get
    @Secured("ROLE_ADMIN")
    public List<PlayerEntity> players() {
        return playerService.findAll();
    }

    @Post
    @Secured(SecurityRule.IS_ANONYMOUS)
    public PlayerEntity newPlayer(@Body PlayerRegistrationRequest playerRegistrationRequest) {
        return this.playerService.newPlayer(playerRegistrationRequest);
    }
}
