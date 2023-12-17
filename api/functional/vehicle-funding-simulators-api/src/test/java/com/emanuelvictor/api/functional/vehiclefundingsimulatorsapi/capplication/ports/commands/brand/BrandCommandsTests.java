package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.SpringBootTests;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid.Commands;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa.BrandJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BrandCommandsTests extends SpringBootTests {

    @Autowired
    private BrandJPARepository brandJPARepository;

    @Autowired
    private Commands<BrandInput, BrandOutput> command;

    @Test
    void mustInsertANewBrand() {
        final String name = "Name";
        final BrandInput insertBrandInput = new BrandInput(name);

        final BrandOutput insertBrandOutput = command.create(insertBrandInput);

        Assertions.assertThat(insertBrandOutput.name()).isEqualTo(name);
        Assertions.assertThat(brandJPARepository.findById(name).orElseThrow().getName()).isEqualTo(name);
    }
}
