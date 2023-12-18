package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.SpringBootTests;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa.BrandJPARepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa.ModelJPA;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa.ModelJPARepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid.Commands;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand.BrandCommandOutput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ModelCommandsTests extends SpringBootTests {

    @Autowired
    private ModelCommands commands;

    @Autowired
    private ModelJPARepository modelJPARepository;

    @Test
    void mustInsertANewModel() {
        final String name = "Name";
        final String modelName = "ModelName";
        final BrandCommandInput brandCommandInput = new BrandCommandInput(name);
        final ModelCommandInput modelCommandInput = new ModelCommandInput(modelName, brandCommandInput);

        final ModelCommandOutput modelCommandOutput = commands.create(modelCommandInput);

        Assertions.assertThat(modelCommandOutput.name()).isEqualTo(modelName);
        final ModelJPA modelJPA = modelJPARepository.findById(modelName).orElseThrow();
        Assertions.assertThat(modelJPA).usingRecursiveComparison().isEqualTo(modelCommandOutput);
        Assertions.assertThat(modelJPA).usingRecursiveComparison().isEqualTo(modelCommandInput);
    }
}
