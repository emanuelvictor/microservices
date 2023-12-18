package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.SpringBootTests;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa.BrandJPA;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid.Commands;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa.BrandJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BrandCommandsTests extends SpringBootTests {

    @Autowired
    private BrandCommands commands;

    @Autowired
    private BrandJPARepository brandJPARepository;

    @Test
    void mustInsertANewBrand() {
        final String name = "Name";
        final BrandCommandInput brandCommandInput = new BrandCommandInput(name);

        final BrandCommandOutput brandCommandOutput = commands.create(brandCommandInput);

        Assertions.assertThat(brandCommandOutput).usingRecursiveComparison().isEqualTo(brandCommandInput);
        final BrandJPA brandJPA = brandJPARepository.findById(name).orElseThrow();
        Assertions.assertThat(brandJPA).usingRecursiveComparison().isEqualTo(brandCommandInput);
        Assertions.assertThat(brandJPA).usingRecursiveComparison().isEqualTo(brandCommandOutput);
    }
}
