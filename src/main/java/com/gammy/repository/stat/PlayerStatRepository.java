package com.gammy.repository.stat;

import com.gammy.model.entity.stat.PlayerStatEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface PlayerStatRepository extends CrudRepository<PlayerStatEntity, Long> {
    Optional<PlayerStatEntity> findByPlayerIdAndGameStatApiName(Long playerId, String gameStatApiName);
}
