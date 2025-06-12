package com.gammy.model.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@Serdeable
public class StatAnalytics {
    private long totalNonDefaultEntries;
    private float averageValue;
    private float standardDeviation;

    private Map<Float, Long> distributionOfValues;

    private BigDecimal mostCommonValue;
    private float percentAboveAverage;
}
