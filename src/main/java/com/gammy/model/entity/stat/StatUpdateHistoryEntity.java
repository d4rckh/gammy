package com.gammy.model.entity.stat;

import com.gammy.model.entity.PlayerEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Serdeable
public class StatUpdateHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "player_id")
    private PlayerEntity player;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "game_stat_id")
    private GameStatEntity gameStat;

    private BigDecimal oldValue;
    private BigDecimal newValue;

    @CreationTimestamp
    private Instant timestamp;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean suspicious = false;
}
