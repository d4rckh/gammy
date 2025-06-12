package com.gammy.model.entity.reporting;

import com.gammy.model.entity.PlayerEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
@Serdeable
public class PlayerReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "reporter_player_id")
    @Nullable
    private PlayerEntity reporterPlayer;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "reported_player_id", nullable = false)
    private PlayerEntity reportedPlayer;

    @ColumnDefault("")
    @Column(nullable = false)
    private String text;
}
