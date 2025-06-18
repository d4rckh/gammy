package com.gammy.service;

import com.gammy.repository.SessionTrackingRepository;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class SessionTrackingService {

    private final SessionTrackingRepository sessionTrackingRepository;
    private final Duration SESSION_INACTIVE_DURATION = Duration.ofSeconds(30);

    public void refreshSession(String sessionId, Long playerId) {
        SessionTrackingRepository.SessionInformation sessionInformation = sessionTrackingRepository
                .getById(sessionId).orElse(new SessionTrackingRepository.SessionInformation());

        sessionInformation.setPlayerId(playerId);
        sessionInformation.setSessionId(sessionId);
        sessionInformation.setLastAccess(Instant.now());

        sessionTrackingRepository.update(sessionInformation);
    }

    private long getActiveSessions() {
        return this.sessionTrackingRepository.countSessionsNewerThan(SESSION_INACTIVE_DURATION);
    }

    @Scheduled(fixedDelay = "5s")
    @ReflectiveAccess
    private void executeJob() {
        log.info("Currently active sessions: {}", this.getActiveSessions());
    }

}
