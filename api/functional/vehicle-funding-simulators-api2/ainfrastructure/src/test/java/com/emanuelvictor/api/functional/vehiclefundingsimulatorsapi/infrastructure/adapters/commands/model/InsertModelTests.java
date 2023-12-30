package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.commands.model;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.SpringBootTests;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.repositories.jpa.ModelJPA;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.repositories.jpa.ModelJPARepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.model.InsertModel;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.model.ModelCommandOutput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InsertModelTests extends SpringBootTests {

    @Autowired
    private InsertModel insertModel;

    @Autowired
    private ModelJPARepository modelJPARepository;

    @Test
    void mustInsertANewModel() {
        final String name = "Name";
        final String modelName = "ModelName";
        final BrandCommandInput brandCommandInput = new BrandCommandInput(name);
        final ModelCommandInput modelCommandInput = new ModelCommandInput(modelName, brandCommandInput);

        final ModelCommandOutput modelCommandOutput = insertModel.execute(modelCommandInput);

        Assertions.assertThat(modelCommandOutput.name()).isEqualTo(modelName);
        final ModelJPA modelJPA = modelJPARepository.findById(modelName).orElseThrow();
        Assertions.assertThat(modelJPA).usingRecursiveComparison().isEqualTo(modelCommandOutput);
        Assertions.assertThat(modelJPA).usingRecursiveComparison().isEqualTo(modelCommandInput);
    }
}
