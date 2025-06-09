package com.gammy.model.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Serdeable
public class PlayerGameStatUpdateRequest {
    private String gameStatApiName;
    private BigDecimal newValue;
    private PlayerGameStatUpdateMethod updateMethod = PlayerGameStatUpdateMethod.FORCE;
}
