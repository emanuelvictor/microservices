package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.SpringBootTests;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.BrandBuilder;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa.BrandJPARepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.BrandRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BrandRepositoryImplTests extends SpringBootTests {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandJPARepository brandJPARepository;

    @Test
    void mustInsertABrand() {
        final var brand = new BrandBuilder().build();

        brandRepository.create(brand);

        final var brandJPA = brandJPARepository.findById(brand.name());
        Assertions.assertThat(brandJPA.isPresent()).isTrue();
    }

    @Test
    void mustFindABrandByName() {
        final var brand = new BrandBuilder().build();
        brandRepository.create(brand);

        final var brandRetrivedByName = brandRepository.findBrandByName(brand.name()).orElseThrow();

        Assertions.assertThat(brandRetrivedByName.name()).isEqualTo(brand.name());
    }

}
