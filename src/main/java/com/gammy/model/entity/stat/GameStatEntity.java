package com.gammy.model.entity.stat;

import com.gammy.model.GameStatType;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Data
@Entity
@Serdeable
public class GameStatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String apiName;

    @Column(nullable = false, unique = true)
    private String displayName;

    @ColumnDefault("")
    @Column(nullable = false)
    private String description = "";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatType type;

    @Column(nullable = false)
    private BigDecimal defaultValue;

    @Nullable
    private BigDecimal maxValue;

    @Nullable
    private BigDecimal minValue;

    @Nullable
    private BigDecimal maxChange;

    @ColumnDefault("false")
    @Column(nullable = false)
    private Boolean onlyIncrement = false;
}
