package com.gammy.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

import java.util.Map;

@Controller("user")
public class UserController {

    @Get
    @Secured(SecurityRule.IS_AUTHENTICATED)
    Map<String, Object> me(Authentication authentication) {
        return Map.of(
                "username", authentication.getName(),
                "attributes", authentication.getAttributes(),
                "roles", authentication.getRoles()
        );
    }

}
