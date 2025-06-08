package com.gammy.repository;

import com.gammy.model.PlayerEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerEntity, Long> {
    Optional<PlayerEntity> findByUsername(String username);
}
