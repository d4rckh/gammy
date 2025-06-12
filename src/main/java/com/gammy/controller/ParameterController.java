package com.gammy.controller;

import com.gammy.model.dto.PlayerOverrideParameterRequest;
import com.gammy.model.entity.parameter.ParameterEntity;
import com.gammy.model.entity.parameter.PlayerParameterOverrideEntity;
import com.gammy.service.AuthorizationService;
import com.gammy.service.ParameterService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Controller("parameters")
@RequiredArgsConstructor
public class ParameterController {

    private final ParameterService parameterService;
    private final AuthorizationService authorizationService;

    @Secured("ROLE_ADMIN")
    @Get
    public List<ParameterEntity> getParameters() {
        return parameterService.getParameters();
    }

    @Secured("ROLE_ADMIN")
    @Post
    public ParameterEntity createParameter(@Body ParameterEntity parameterEntity) {
        return parameterService.createParameter(parameterEntity);
    }

    @Secured("ROLE_ADMIN")
    @Put
    public ParameterEntity updateParameter(@Body ParameterEntity parameterEntity) {
        return parameterService.updateParameter(parameterEntity);
    }

    @Secured("ROLE_ADMIN")
    @Delete
    public void deleteParameter(@Body ParameterEntity parameterEntity) {
        parameterService.deleteParameter(parameterEntity);
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("player/{playerId}")
    public Map<String, String> getParametersForPlayer(@PathVariable Long playerId) {
        authorizationService.throwIfCantReadPlayerId(playerId);
        return parameterService.getParametersForPlayer(playerId);
    }

    @Secured("ROLE_ADMIN")
    @Get("overrides/{playerId}")
    public List<PlayerParameterOverrideEntity> getPlayerParameterOverrides(@PathVariable Long playerId) {
        return parameterService.getPlayerParameterOverrides(playerId);
    }

    @Secured("ROLE_ADMIN")
    @Post("overrides")
    public PlayerParameterOverrideEntity createPlayerParameterOverride(
            @Body PlayerOverrideParameterRequest playerOverrideParameterRequest) {
        return parameterService.overridePlayerParameter(
                playerOverrideParameterRequest.getPlayerId(),
                playerOverrideParameterRequest.getPath(),
                playerOverrideParameterRequest.getValue()
        );
    }

    @Secured("ROLE_ADMIN")
    @Delete("overrides")
    public void deletePlayerParameterOverride(
            @Body PlayerParameterOverrideEntity playerParameterOverrideEntity) {
        parameterService.deletePlayerParameterOverride(playerParameterOverrideEntity);
    }

    @Secured("ROLE_ADMIN")
    @Put("overrides")
    public PlayerParameterOverrideEntity updatePlayerParameterOverride(
            @Body PlayerParameterOverrideEntity playerParameterOverrideEntity) {
        return parameterService.updatePlayerParameterOverride(playerParameterOverrideEntity);
    }
}
