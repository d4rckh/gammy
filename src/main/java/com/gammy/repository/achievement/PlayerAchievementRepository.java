package com.gammy.repository.achievement;

import com.gammy.model.entity.achievement.PlayerAchievementEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface PlayerAchievementRepository extends CrudRepository<PlayerAchievementEntity, Integer> {
    Optional<PlayerAchievementEntity> findByPlayerIdAndGameAchievementApiName(Long playerId, String gameAchievementApiName);
    Long countByGameAchievementApiName(String gameAchievementApiName);
}
