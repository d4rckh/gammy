package com.gammy.model.entity.interaction;

import com.gammy.model.entity.PlayerEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Data
@Entity
@Serdeable
public class PlayerInteractionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_interaction_id", nullable = false)
    private GameInteractionEntity gameInteraction;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    @CreationTimestamp
    private Instant timestamp;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long streak = 0L;
}
