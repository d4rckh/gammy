package com.gammy.service;

import com.gammy.model.IParameter;
import com.gammy.model.entity.parameter.ParameterEntity;
import com.gammy.model.entity.parameter.PlayerParameterOverrideEntity;
import com.gammy.repository.parameter.ParameterRepository;
import com.gammy.repository.parameter.PlayerParameterOverrideRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
public class ParameterService {

    private final ParameterRepository parameterRepository;
    private final PlayerService playerService;
    private final PlayerParameterOverrideRepository playerParameterOverrideRepository;

    public List<ParameterEntity> getParameters() {
        return parameterRepository.findAll();
    }

    public ParameterEntity createParameter(ParameterEntity parameterEntity) {
        return parameterRepository.save(parameterEntity);
    }

    public ParameterEntity updateParameter(ParameterEntity parameterEntity) {
        return parameterRepository.update(parameterEntity);
    }

    public void deleteParameter(ParameterEntity parameterEntity) {
        parameterRepository.delete(parameterEntity);
    }

    public PlayerParameterOverrideEntity createPlayerParameterOverride(PlayerParameterOverrideEntity playerParameterOverrideEntity) {
        return playerParameterOverrideRepository.save(playerParameterOverrideEntity);
    }

    public PlayerParameterOverrideEntity updatePlayerParameterOverride(PlayerParameterOverrideEntity playerParameterOverrideEntity) {
        return playerParameterOverrideRepository.save(playerParameterOverrideEntity);
    }

    public void deletePlayerParameterOverride(PlayerParameterOverrideEntity playerParameterOverrideEntity) {
        playerParameterOverrideRepository.delete(playerParameterOverrideEntity);
    }

    public List<PlayerParameterOverrideEntity> getPlayerParameterOverrides(Long playerId) {
        return playerParameterOverrideRepository.findByPlayerId(playerId);
    }

    public PlayerParameterOverrideEntity overridePlayerParameter(Long playerId, String parameterPath, String value) {
        PlayerParameterOverrideEntity playerParameterOverrideEntity =
                this.playerParameterOverrideRepository.findByPlayerIdAndParameterPath(playerId, parameterPath)
                        .orElseGet(() -> {
                            PlayerParameterOverrideEntity playerParameterOverrideEntity1 = new PlayerParameterOverrideEntity();
                            playerParameterOverrideEntity1.setPlayer(
                                    this.playerService.findById(playerId).orElseThrow()
                            );
                            playerParameterOverrideEntity1.setParameter(
                                    this.parameterRepository.findByPath(parameterPath).orElseThrow()
                            );
                            return playerParameterOverrideEntity1;
                        });

        playerParameterOverrideEntity.setValue(value);

        if (playerParameterOverrideEntity.getId() == null) {
            return this.playerParameterOverrideRepository.save(playerParameterOverrideEntity);
        } else {
            return this.playerParameterOverrideRepository.update(playerParameterOverrideEntity);
        }
    }

    public Map<String, String> getParametersForPlayer(Long playerId) {
        Map<String, String> parameters = this.flattenParameters(this.getParameters());
        Map<String, String> playerParameterOverrides = this.flattenParameters(this.getPlayerParameterOverrides(playerId));

        Map<String, String> combinedParameters = new HashMap<>(parameters);

        combinedParameters.putAll(playerParameterOverrides);

        return combinedParameters;
    }

    protected <T extends IParameter> Map<String, String> flattenParameters(List<T> parameterEntities) {
        return parameterEntities.stream().collect(Collectors.toMap(IParameter::getPath, IParameter::getValue));
    }
}
