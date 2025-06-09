package com.gammy.authentication;

import com.gammy.model.entity.PlayerEntity;
import com.gammy.service.PlayerService;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.HttpRequestReactiveAuthenticationProvider;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class AuthenticationProviderUserPassword<B> implements HttpRequestReactiveAuthenticationProvider<B> {

    public static final String PLAYER_PREFIX = "player_";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_PLAYER = "ROLE_PLAYER";
    public static final String ADMIN_PASSWORD = "admin";
    public static final String ADMIN_USER = "admin";
    private final PlayerService playerService;

    @Override
    public Publisher<AuthenticationResponse> authenticate(
            @Nullable HttpRequest<B> httpRequest,
            @NonNull AuthenticationRequest<String, String> authenticationRequest
    ) {
        String identity = authenticationRequest.getIdentity();
        String secret = authenticationRequest.getSecret();

        if (identity == null || identity.isEmpty()) {
            return Mono.error(AuthenticationResponse.exception());
        }

        // ADMIN AUTHENTICATION

        if (ADMIN_USER.equals(identity) && ADMIN_PASSWORD.equals(secret)) {
            return Mono.just(AuthenticationResponse.success(identity, List.of(ROLE_ADMIN)));
        }

        // PLAYER AUTHENTICATION

        if (!identity.startsWith(PLAYER_PREFIX)) {
            return Mono.error(AuthenticationResponse.exception());
        }

        String playerName = identity.substring(PLAYER_PREFIX.length());
        Optional<PlayerEntity> playerOpt = playerService.findByUsername(playerName);

        if (playerOpt.isEmpty()) {
            return Mono.error(AuthenticationResponse.exception());
        }

        if (!BCrypt.checkpw(secret, playerOpt.get().getHashedPassword())) {
            return Mono.error(AuthenticationResponse.exception());
        }

        return Mono.just(AuthenticationResponse.success(
                playerOpt.get().getUsername(),
                List.of(ROLE_PLAYER),
                Map.of("playerId", playerOpt.get().getId())
        ));
    }
}