package com.gammy.model.entity.parameter;

import com.gammy.model.IParameter;
import com.gammy.model.entity.PlayerEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"parameter_id", "player_id"})
})
@Serdeable
public class PlayerParameterOverrideEntity implements IParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "parameter_id", nullable = false)
    private ParameterEntity parameter;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    @Column(nullable = false)
    private String value;

    @Override
    public String getPath() {
        return this.getParameter().getPath();
    }
}