package com.gammy.model.entity.leaderboard;

import com.gammy.model.entity.stat.GameStatEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
@Serdeable
public class LeaderboardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private String apiName;

    @Column(nullable = false)
    @ColumnDefault("")
    private String description = "";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaderboardSortMethod sortMethod;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_stat_id", nullable = false)
    private GameStatEntity gameStat;
}
