package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.repositories;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.builders.ModelBuilder;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.ModelRepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.SpringBootTests;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.repositories.jpa.ModelJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ModelRepositoryImplTests extends SpringBootTests {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ModelJPARepository modelJPARepository;

    @Test
    void mustInsertAModel() {
        final var model = new ModelBuilder().build();

        modelRepository.create(model);

        final var modelJPA = modelJPARepository.findById(model.name());
        Assertions.assertThat(modelJPA.isPresent()).isTrue();
    }

    @Test
    void mustFindAModelByNameAndBrandName() {
        final var model = new ModelBuilder().build();
        modelRepository.create(model);

        final var modelRetrived = modelRepository
                .findModelByNameAndBrandName(model.name(), model.getBrandName())
                .orElseThrow();

        Assertions.assertThat(modelRetrived.name()).isEqualTo(model.name());
        Assertions.assertThat(modelRetrived.getBrandName()).isEqualTo(model.getBrandName());
    }
}
