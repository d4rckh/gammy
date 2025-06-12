package com.gammy.controller;

import com.gammy.model.dto.LeaderboardCreateRequest;
import com.gammy.model.dto.LeaderboardEntries;
import com.gammy.model.entity.leaderboard.LeaderboardEntity;
import com.gammy.service.LeaderboardService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller("leaderboards")
@RequiredArgsConstructor
public class LeaderboardController {
    private final LeaderboardService leaderboardService;

    @Post
    @Secured("ROLE_ADMIN")
    LeaderboardEntity createLeaderboard(@Body LeaderboardCreateRequest leaderboardCreateRequest) {
        return leaderboardService.createLeaderboard(leaderboardCreateRequest);
    }

    @Get
    @Secured("ROLE_ADMIN")
    List<LeaderboardEntity> getLeaderboards() {
        return leaderboardService.getLeaderboards();
    }

    @Put
    @Secured("ROLE_ADMIN")
    LeaderboardEntity updateLeaderboard(@Body LeaderboardEntity leaderboardEntity) {
        return leaderboardService.updateLeaderboard(leaderboardEntity);
    }

    @Delete
    @Secured("ROLE_ADMIN")
    void deleteLeaderboard(@Body LeaderboardEntity leaderboardEntity) {
        leaderboardService.deleteLeaderboard(leaderboardEntity);
    }

    @Get("{apiName}")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    LeaderboardEntries getLeaderboardEntries(String apiName) {
        return leaderboardService.getLeaderboardEntries(apiName);
    }
}
