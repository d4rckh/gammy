package com.gammy.filter;

import com.gammy.service.SessionTrackingService;
import io.micronaut.core.order.Ordered;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.http.annotation.ServerFilter;
import io.micronaut.http.filter.ServerFilterPhase;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.session.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.gammy.authentication.AuthenticationProviderUserPassword.PLAYER_ID;
import static com.gammy.authentication.AuthenticationProviderUserPassword.ROLE_PLAYER;
import static io.micronaut.http.annotation.Filter.MATCH_ALL_PATTERN;

@Slf4j
@ServerFilter(MATCH_ALL_PATTERN)
@RequiredArgsConstructor
public class SessionTrackingFilter implements Ordered {

    private final SessionTrackingService sessionTrackingService;

    private Optional<Session> getUserSession(final HttpRequest<?> request) {
        return request.getAttributes().get("micronaut.SESSION", Session.class);
    }

    private Optional<Authentication> getUserAuthentication(final HttpRequest<?> request) {
        return request.getAttributes().get("micronaut.AUTHENTICATION", Authentication.class);
    }

    @RequestFilter
    void filter(HttpRequest<?> request) {
        Optional<Authentication> authentication = getUserAuthentication(request);
        Optional<Session> session = getUserSession(request);

        if (authentication.isEmpty() || session.isEmpty()) {
            return;
        }

        String sessionId = session.get().getId();
        Long playerId = (Long) authentication.get().getAttributes().get(PLAYER_ID);

        sessionTrackingService.refreshSession(sessionId, playerId);
    }

    @Override
    public int getOrder() {
        return ServerFilterPhase.LAST.order();
    }
}
