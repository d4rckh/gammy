package com.gammy.service;

import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class AuthorizationService {

    private final SecurityService securityService;

    private Long currentLoggedUserId() {
        return (Long) this.securityService.getAuthentication().orElseThrow().getAttributes().get("playerId");
    }

    private Boolean currentLoggedUserIsAdmin() {
        return this.securityService.getAuthentication().orElseThrow().getRoles().contains("ROLE_ADMIN");
    }

    public boolean canReadPlayerId(Long playerId) {
        if (this.currentLoggedUserIsAdmin())
            return true;

        if (this.currentLoggedUserId() == null)
            return false;

        return this.currentLoggedUserId().equals(playerId);
    }

    public boolean canWritePlayerId(Long playerId) {
        return this.canReadPlayerId(playerId);
    }

    public void throwIfCantReadPlayerId(Long playerId) {
        if (!this.canReadPlayerId(playerId)) {
            throw new SecurityException("You do not have permission to read player id " + playerId);
        }
    }

    public void throwIfCantWritePlayerId(Long playerId) {
        if (!this.canWritePlayerId(playerId)) {
            throw new SecurityException("You do not have permission to write player id " + playerId);
        }
    }

}
