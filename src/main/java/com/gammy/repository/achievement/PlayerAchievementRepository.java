package com.gammy.repository.achievement;

import com.gammy.model.entity.achievement.PlayerAchievementEntity;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.annotation.Nullable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerAchievementRepository extends CrudRepository<PlayerAchievementEntity, Integer> {
    Optional<PlayerAchievementEntity> findByPlayerIdAndGameAchievementApiName(Long playerId, String gameAchievementApiName);

    Long countByGameAchievementApiName(String gameAchievementApiName);

    @Query("""
            SELECT p FROM PlayerAchievementEntity p
                WHERE (:playerId IS NULL OR p.player.id = :playerId)
                AND (:apiName IS NULL OR p.gameAchievement.apiName = :apiName)
                AND (cast(:createdAfter as java.time.Instant) IS NULL OR p.createdAt >= :createdAfter)
            """)
    List<PlayerAchievementEntity> find(@Nullable Long playerId, @Nullable String apiName, @Nullable Instant createdAfter);
}
