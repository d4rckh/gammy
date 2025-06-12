package com.gammy.service;

import com.gammy.model.dto.LeaderboardCreateRequest;
import com.gammy.model.dto.LeaderboardEntries;
import com.gammy.model.dto.LeaderboardEntry;
import com.gammy.model.entity.leaderboard.LeaderboardEntity;
import com.gammy.model.entity.stat.GameStatEntity;
import com.gammy.model.entity.stat.PlayerStatEntity;
import com.gammy.repository.leaderboard.LeaderboardRepository;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.IntStream;

@Singleton
@RequiredArgsConstructor
public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;
    private final StatService statService;

    public List<LeaderboardEntity> getLeaderboards() {
        return this.leaderboardRepository.findAll();
    }

    public LeaderboardEntity updateLeaderboard(LeaderboardEntity leaderboardEntity) {
        return leaderboardRepository.update(leaderboardEntity);
    }

    public void deleteLeaderboard(LeaderboardEntity leaderboardEntity) {
        leaderboardRepository.delete(leaderboardEntity);
    }

    public LeaderboardEntity createLeaderboard(LeaderboardCreateRequest leaderboardCreateRequest) {
        LeaderboardEntity leaderboardEntity = new LeaderboardEntity();

        GameStatEntity gameStatEntity = this.statService.findByApiName(leaderboardCreateRequest.getApiName()).orElseThrow();

        leaderboardEntity.setDescription(leaderboardCreateRequest.getDescription());
        leaderboardEntity.setDisplayName(leaderboardCreateRequest.getDisplayName());
        leaderboardEntity.setApiName(leaderboardCreateRequest.getApiName());
        leaderboardEntity.setGameStat(gameStatEntity);
        leaderboardEntity.setSortMethod(leaderboardCreateRequest.getSortMethod());

        return leaderboardRepository.save(leaderboardEntity);
    }

    public LeaderboardEntries getLeaderboardEntries(String leaderboardApiName) {
        LeaderboardEntity leaderboardEntity = this.leaderboardRepository.findByApiName(leaderboardApiName).orElseThrow();

        List<PlayerStatEntity> playerStatEntities = this.statService.getOrderedGameStatScores(leaderboardApiName, leaderboardEntity.getSortMethod());

        List<LeaderboardEntry> entries = IntStream.range(0, playerStatEntities.size())
                .mapToObj(i -> {
                    PlayerStatEntity stat = playerStatEntities.get(i);
                    int rank = i + 1;
                    return new LeaderboardEntry(stat.getPlayer(), stat.getValue(), (long) rank);
                })
                .toList();

        return new LeaderboardEntries(leaderboardEntity, entries);
    }
}
