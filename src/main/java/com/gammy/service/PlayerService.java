package com.gammy.service;

import com.gammy.model.entity.PlayerEntity;
import com.gammy.model.dto.PlayerRegistrationRequest;
import com.gammy.repository.PlayerRepository;
import io.micronaut.security.authentication.AuthenticationException;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Optional<PlayerEntity> findById(Long id) {
        return playerRepository.findById(id);
    }

    public Optional<PlayerEntity> findByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    public List<PlayerEntity> findAll() {
        return playerRepository.findAll();
    }

    public PlayerEntity newPlayer(PlayerRegistrationRequest playerRegistrationRequest) {
        if (this.findByUsername(playerRegistrationRequest.getUsername()).isPresent()) {
            throw new AuthenticationException("Username is already in use");
        }

        PlayerEntity playerEntity = new PlayerEntity();

        playerEntity.setUsername(playerRegistrationRequest.getUsername());
        playerEntity.setHashedPassword(BCrypt.hashpw(playerRegistrationRequest.getPassword(), BCrypt.gensalt()));

        return playerRepository.save(playerEntity);
    }

}
