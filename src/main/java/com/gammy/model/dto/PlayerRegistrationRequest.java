package com.gammy.model.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Data
@Serdeable
public class PlayerRegistrationRequest {
    private String username;
    private String password;
}
