package com.gammy.model.entity.parameter;

import com.gammy.model.IParameter;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Serdeable
public class ParameterEntity implements IParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParameterType type;

    @Column(nullable = false)
    private String value;
}