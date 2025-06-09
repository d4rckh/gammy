package com.gammy.repository.stat;

import com.gammy.model.entity.stat.StatUpdateHistoryEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface StatUpdateHistoryRepository extends CrudRepository<StatUpdateHistoryEntity, Long> {
    List<StatUpdateHistoryEntity> findByPlayerId(Long playerId);

    List<StatUpdateHistoryEntity> findByPlayerIdAndGameStatApiName(Long playerId, String statApiName);
}
