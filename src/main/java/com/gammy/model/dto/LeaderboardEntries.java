package com.gammy.model.dto;

import com.gammy.model.entity.leaderboard.LeaderboardEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

import java.util.List;

@Data
@Serdeable
public class LeaderboardEntries {
    private final LeaderboardEntity leaderboard;
    private final List<LeaderboardEntry> entries;
}
