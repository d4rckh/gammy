package com.gammy.repository.interaction;

import com.gammy.model.entity.interaction.PlayerInteractionEntity;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerInteractionRepository extends CrudRepository<PlayerInteractionEntity, Long> {
    @Query("""
                SELECT p FROM PlayerInteractionEntity p
                WHERE p.player.id = :playerId AND p.gameInteraction.apiName = :gameInteractionApiName
                ORDER BY p.timestamp DESC
            """)
    Optional<PlayerInteractionEntity> findLastByPlayerIdAndGameInteractionApiNameOrderByTimestampDesc(Long playerId, String gameInteractionApiName);

    List<PlayerInteractionEntity> findAllByGameInteractionApiName(String gameInteractionApiName);

    List<PlayerInteractionEntity> findAllByPlayerId(Long playerId);

    Long countByGameInteractionApiName(String gameInteractionApiName);

    @Query("""
                SELECT p FROM Player
            """)
    List<PlayerInteractionEntity> findFiltered(
            Long playerId,
            String gameInteractionApiName
    );
}
