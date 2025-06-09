package com.gammy.repository.interaction;

import com.gammy.model.entity.interaction.GameInteractionEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface GameInteractionRepository extends CrudRepository<GameInteractionEntity, Long> {
    Optional<GameInteractionEntity> findByApiName(String apiName);
}
