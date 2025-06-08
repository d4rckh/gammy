package com.gammy.authentication;

import com.gammy.PlayerService;
import com.gammy.model.PlayerEntity;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.provider.HttpRequestReactiveAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.List;
import java.util.Optional;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class AuthenticationProviderUserPassword<B> implements HttpRequestReactiveAuthenticationProvider<B> {

    private final PlayerService playerService;

    @Override
    public Publisher<AuthenticationResponse> authenticate(
            @Nullable HttpRequest<B> httpRequest,
            @NonNull AuthenticationRequest<String, String> authenticationRequest
    ) {
        return Flux.create(emitter -> {
            if (authenticationRequest.getIdentity().startsWith("player_")) {
                String playerName = authenticationRequest.getIdentity().replaceFirst("player_", "");

                Optional<PlayerEntity> playerEntityOptional = playerService.findByUsername(playerName);

                if (playerEntityOptional.isEmpty()) {
                    emitter.error(AuthenticationResponse.exception());
                    return;
                }

                emitter.next(
                        AuthenticationResponse.success(playerEntityOptional.get().getUsername(),
                                List.of("ROLE_PLAYER"))
                );

                emitter.complete();
            }

            if (!authenticationRequest.getIdentity().equals("admin") ||
                    !authenticationRequest.getSecret().equals("admin")) {
                emitter.error(AuthenticationResponse.exception());
                return;
            }

            emitter.next(AuthenticationResponse.success(
                    authenticationRequest.getIdentity(),
                    List.of("ROLE_ADMIN")
            ));
            emitter.complete();
        }, FluxSink.OverflowStrategy.ERROR);
    }
}
