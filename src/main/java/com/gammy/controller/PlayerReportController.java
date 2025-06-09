package com.gammy.controller;

import com.gammy.model.dto.ReportPlayerRequest;
import com.gammy.model.entity.reporting.PlayerReportEntity;
import com.gammy.service.AuthorizationService;
import com.gammy.service.PlayerReportService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller("reports")
@RequiredArgsConstructor
public class PlayerReportController {
    private final PlayerReportService reportService;
    private final AuthorizationService authorizationService;

    @Post
    @Secured(SecurityRule.IS_AUTHENTICATED)
    PlayerReportEntity reportPlayer(@Body ReportPlayerRequest reportPlayerRequest) {
        authorizationService.canWritePlayerId(reportPlayerRequest.getReporterPlayerId());

        return this.reportService.createReport(reportPlayerRequest);
    }

    @Get("by/{playerId}")
    @Secured("ROLE_ADMIN")
    List<PlayerReportEntity> reportByPlayerId(@PathVariable("playerId") Long playerId) {
        return this.reportService.getReportsBy(playerId);
    }

    @Get("for/{playerId}")
    @Secured("ROLE_ADMIN")
    List<PlayerReportEntity> reportForPlayerId(@PathVariable("playerId") Long playerId) {
        return this.reportService.getReportsFor(playerId);
    }
}
