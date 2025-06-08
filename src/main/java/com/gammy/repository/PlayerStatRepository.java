package com.gammy.repository;

import com.gammy.model.PlayerStatEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerStatRepository extends CrudRepository<PlayerStatEntity, Long> {
    Optional<PlayerStatEntity> findByPlayerIdAndGameStatApiName(Long playerId, String gameStatApiName);
}
