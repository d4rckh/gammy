package com.gammy.repository;

import com.gammy.model.GameStatEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface GameStatRepository extends CrudRepository<GameStatEntity, Long> {
    Optional<GameStatEntity> findByApiName(String apiName);
}
