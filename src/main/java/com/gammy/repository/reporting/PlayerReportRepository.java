package com.gammy.repository.reporting;

import com.gammy.model.entity.reporting.PlayerReportEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface PlayerReportRepository extends CrudRepository<PlayerReportEntity, Long> {
    List<PlayerReportEntity> findByReportedPlayerId(Long reportedPlayerId);

    List<PlayerReportEntity> findByReporterPlayerId(Long reporterPlayerId);
}
