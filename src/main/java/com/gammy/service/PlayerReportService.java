package com.gammy.service;

import com.gammy.model.dto.ReportPlayerRequest;
import com.gammy.model.entity.reporting.PlayerReportEntity;
import com.gammy.repository.reporting.PlayerReportRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Singleton
@RequiredArgsConstructor
public class PlayerReportService {

    private final PlayerReportRepository playerReportRepository;
    private final PlayerService playerService;

    public PlayerReportEntity createReport(ReportPlayerRequest reportPlayerRequest) {
        PlayerReportEntity playerReportEntity = new PlayerReportEntity();

        if (reportPlayerRequest.getReportedPlayerId()
                .equals(reportPlayerRequest.getReporterPlayerId())) {
            throw new RuntimeException("Reported player id is the same as player user id");
        }

        playerReportEntity.setReporterPlayer(
                playerService.findById(reportPlayerRequest.getReporterPlayerId()).orElseThrow()
        );
        playerReportEntity.setReportedPlayer(
                playerService.findById(reportPlayerRequest.getReportedPlayerId()).orElseThrow()
        );

        playerReportEntity.setText(reportPlayerRequest.getText());

        return playerReportRepository.save(playerReportEntity);
    }

    public List<PlayerReportEntity> getReportsFor(Long reportedPlayerId) {
        return playerReportRepository.findByReportedPlayerId(reportedPlayerId);
    }

    public List<PlayerReportEntity> getReportsBy(Long reporterPlayerId) {
        return playerReportRepository.findByReporterPlayerId(reporterPlayerId);
    }
}
