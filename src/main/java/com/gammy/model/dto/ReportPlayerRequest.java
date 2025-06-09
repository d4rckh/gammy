package com.gammy.model.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Data
@Serdeable
public class ReportPlayerRequest {
    private final Long reporterPlayerId;
    private final Long reportedPlayerId;
    private final String text;
}
