package com.gammy.model.entity.stat;

import com.gammy.model.entity.PlayerEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@Serdeable
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"player_id", "game_stat_id"})
        }
)
public class PlayerStatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_stat_id", nullable = false)
    private GameStatEntity gameStat;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    @Column(nullable = false)
    private BigDecimal value;

    @UpdateTimestamp
    private Instant lastUpdate;
}
