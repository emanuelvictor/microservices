package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.SpringBootTests;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.builders.BrandBuilder;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.jpa.BrandJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

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

    @Test
    void listBrandsByFilter() {
        final var brand = "brand";
        final var brandName = brand + "Name";
        final var vehicle = new BrandBuilder()
                .name(brandName)
                .build();
        brandRepository.create(vehicle);
        for (int i = 0; i < 15; i++) {
            brandRepository.create(new BrandBuilder()
                    .name(UUID.randomUUID().toString())
                    .build());
        }

        final var pageOfVehiclesFilteredByBrandName = brandRepository
                .getAPageOfBrandsByFilters(brand, PageRequest.of(0, 5));

        Assertions.assertThat(pageOfVehiclesFilteredByBrandName.getContent().size()).isEqualTo(1);
        Assertions.assertThat(pageOfVehiclesFilteredByBrandName.getContent()).extracting(Brand::name)
                .contains(brandName);
    }

}
