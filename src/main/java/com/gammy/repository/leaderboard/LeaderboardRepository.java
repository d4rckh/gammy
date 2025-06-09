package com.gammy.repository.leaderboard;

import com.gammy.model.entity.leaderboard.LeaderboardEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface LeaderboardRepository extends CrudRepository<LeaderboardEntity, Long> {
    Optional<LeaderboardEntity> findByApiName(String apiName);
}
