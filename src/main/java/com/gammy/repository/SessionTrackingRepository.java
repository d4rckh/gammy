package com.gammy.repository;

import com.gammy.service.SessionTrackingService;
import io.micronaut.data.annotation.Repository;
import jakarta.annotation.Nullable;
import jakarta.inject.Singleton;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class SessionTrackingRepository {
    @Data
    public static class SessionInformation {
        private String sessionId;
        private Instant lastAccess;
        private Long playerId;
    }

    private final Map<String, SessionInformation> sessions = new HashMap<>();

    public Optional<SessionInformation> getById(String sessionId) {
        if (sessions.containsKey(sessionId)) {
            return Optional.of(sessions.get(sessionId));
        }
        return Optional.empty();
    }

    public void update(SessionInformation sessionInformation) {
        sessions.put(sessionInformation.getSessionId(), sessionInformation);
    }

    public long countSessionsNewerThan(Duration sessionInactiveDuration) {
        return sessions.values().stream()
                .filter(s -> s.lastAccess.isAfter(Instant.now().minus(sessionInactiveDuration)))
                .count();
    }
}
