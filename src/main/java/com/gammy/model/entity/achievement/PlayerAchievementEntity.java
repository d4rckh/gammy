package com.gammy.model.entity.achievement;

import com.gammy.model.ICountableInTime;
import com.gammy.model.entity.PlayerEntity;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Serdeable
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"game_achievement_id", "player_id"})
        }
)
public class PlayerAchievementEntity implements ICountableInTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_achievement_id", nullable = false)
    private GameAchievementEntity gameAchievement;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    @DateCreated
    private Instant createdAt;

    @Override
    public Instant getTimestamp() {
        return this.getCreatedAt();
    }
}
