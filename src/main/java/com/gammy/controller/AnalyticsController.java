package com.gammy.controller;

import com.gammy.model.dto.GameAnalytics;
import com.gammy.model.dto.StatAnalytics;
import com.gammy.service.AnalyticsService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import lombok.RequiredArgsConstructor;

@Controller("analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @Get
    @Secured("ROLE_ADMIN")
    GameAnalytics getGameAnalytics() {
        return analyticsService.getGameAnalytics();
    }

    @Get("stat/{apiName}")
    @Secured("ROLE_ADMIN")
    StatAnalytics getStatAnalytics(String apiName) {
        return analyticsService.getStatAnalytics(apiName);
    }
}
