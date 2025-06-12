package com.gammy.model.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Serdeable
@Data
public class PlayerOverrideParameterRequest {
    private String value;
    private String path;
    private Long playerId;
}
