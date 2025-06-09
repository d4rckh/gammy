package com.gammy.model.entity.achievement;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
@Serdeable
public class GameAchievementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String apiName;

    @Column(nullable = false)
    @ColumnDefault("")
    private String description = "";

    @Nullable
    private String unlockExpression;
}
