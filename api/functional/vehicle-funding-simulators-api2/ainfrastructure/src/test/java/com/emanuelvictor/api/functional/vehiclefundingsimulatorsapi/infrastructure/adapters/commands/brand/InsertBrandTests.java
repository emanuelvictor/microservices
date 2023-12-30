package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.commands.brand;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.SpringBootTests;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.repositories.jpa.BrandJPA;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.repositories.jpa.BrandJPARepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand.BrandCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand.InsertBrand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InsertBrandTests extends SpringBootTests {

    @Autowired
    private InsertBrand insertBrand;

    @Autowired
    private BrandJPARepository brandJPARepository;

    @Test
    void mustInsertANewBrand() {
        final String name = "Name";
        final BrandCommandInput brandCommandInput = new BrandCommandInput(name);

        final BrandCommandOutput brandCommandOutput = insertBrand.execute(brandCommandInput);

        Assertions.assertThat(brandCommandOutput).usingRecursiveComparison().isEqualTo(brandCommandInput);
        final BrandJPA brandJPA = brandJPARepository.findById(name).orElseThrow();
        Assertions.assertThat(brandJPA).usingRecursiveComparison().isEqualTo(brandCommandInput);
        Assertions.assertThat(brandJPA).usingRecursiveComparison().isEqualTo(brandCommandOutput);
    }
}
