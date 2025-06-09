package com.gammy.controller;

import com.gammy.model.dto.LeaderboardCreateRequest;
import com.gammy.model.dto.LeaderboardEntries;
import com.gammy.model.entity.leaderboard.LeaderboardEntity;
import com.gammy.service.LeaderboardService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

@Controller("leaderboards")
@RequiredArgsConstructor
public class LeaderboardController {
    private final LeaderboardService leaderboardService;

    @Post
    @Secured("ROLE_ADMIN")
    LeaderboardEntity createLeaderboard(@Body LeaderboardCreateRequest leaderboardCreateRequest) {
        return leaderboardService.createLeaderboard(leaderboardCreateRequest);
    }

    @Get("{apiName}")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    LeaderboardEntries getLeaderboard(String apiName) {
        return leaderboardService.getLeaderboardEntries(apiName);
    }
}
