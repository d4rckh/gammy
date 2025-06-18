package com.gammy.repository.parameter;

import com.gammy.model.entity.parameter.PlayerParameterOverrideEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerParameterOverrideRepository extends CrudRepository<PlayerParameterOverrideEntity, Long> {
    Optional<PlayerParameterOverrideEntity> findByPlayerIdAndParameterPath(Long playerId, String parameterPath);

    List<PlayerParameterOverrideEntity> findByPlayerId(Long playerId);
}
