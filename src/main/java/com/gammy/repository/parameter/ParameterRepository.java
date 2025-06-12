package com.gammy.repository.parameter;

import com.gammy.model.entity.parameter.ParameterEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface ParameterRepository extends CrudRepository<ParameterEntity, Long> {
    Optional<ParameterEntity> findByPath(String path);
}
