package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.insert;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.SpringBootTests;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.Command;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.repositories.jpa.BrandJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InsertBrandCommandServiceTests extends SpringBootTests {

    @Autowired
    private BrandJPARepository brandJPARepository;

    @Autowired
    private Command<InsertBrandInput, InsertBrandOutput> command;

    @Test
    void mustInsertANewBrand() {
        final String name = "Name";
        final InsertBrandInput insertBrandInput = new InsertBrandInput(name);

        final InsertBrandOutput insertBrandOutput = command.execute(insertBrandInput);

        Assertions.assertThat(insertBrandOutput.name()).isEqualTo(name);
        Assertions.assertThat(brandJPARepository.findById(name).orElseThrow().getName()).isEqualTo(name);
    }
}
