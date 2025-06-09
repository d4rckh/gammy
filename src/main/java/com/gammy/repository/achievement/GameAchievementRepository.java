package com.gammy.repository.achievement;

import com.gammy.model.entity.achievement.GameAchievementEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface GameAchievementRepository extends CrudRepository<GameAchievementEntity, Integer> {
    Optional<GameAchievementEntity> findByApiName(String apiName);
}
