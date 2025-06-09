package com.gammy.model.dto;

import com.gammy.model.entity.leaderboard.LeaderboardSortMethod;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Data
@Serdeable
public class LeaderboardCreateRequest {
    private String displayName;
    private String apiName;
    private String description = "";
    private LeaderboardSortMethod sortMethod;
    private String gameStatApiName;
}
