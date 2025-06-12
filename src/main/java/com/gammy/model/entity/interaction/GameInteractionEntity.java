package com.gammy.model.entity.interaction;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
@Serdeable
public class GameInteractionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String apiName;

    @Column(nullable = false, unique = true)
    private String displayName;

    @Column(nullable = false)
    @ColumnDefault("NONE")
    @Enumerated(EnumType.STRING)
    private GameInteractionStreak streak = GameInteractionStreak.NONE;
}
