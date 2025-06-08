package com.gammy.authentication;

import io.micronaut.security.authentication.AuthenticationRequest;
import lombok.Data;

@Data
public class UserAuthenticationRequest implements AuthenticationRequest<String, String> {
    private final String identity;
    private final String secret;
}
