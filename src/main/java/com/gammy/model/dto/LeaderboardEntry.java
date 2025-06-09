package com.gammy.model.dto;

import com.gammy.model.entity.PlayerEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Serdeable
public class LeaderboardEntry {
    private final PlayerEntity player;
    private final BigDecimal value;
    private final Long rank;
}
