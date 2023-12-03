package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.ports.repositories;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.SpringBootTests;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle.BrandBuilder;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle.ModelBuilder;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle.VehicleBuilder;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.repositories.jpa.BrandJPARepository;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.repositories.jpa.ModelJPARepository;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.repositories.jpa.VehicleJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class VehicleRepositoryTests extends SpringBootTests {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    BrandJPARepository brandJPARepository;

    @Autowired
    ModelJPARepository modelJPARepository;

    @Autowired
    VehicleJPARepository vehicleJPARepository;

    @Test
    void mustInsertABrand() {
        final var brand = new BrandBuilder().build();

        vehicleRepository.save(brand);

        final var brandJPA = brandJPARepository.findById(brand.name());
        Assertions.assertThat(brandJPA.isPresent()).isTrue();
    }

    @Test
    void mustInsertAModel() {
        final var model = new ModelBuilder().build();

        vehicleRepository.save(model);

        final var modelJPA = modelJPARepository.findById(model.name());
        Assertions.assertThat(modelJPA.isPresent()).isTrue();
    }

    @Test
    void mustInsertABrandWhenInsertAModel() {
        final var model = new ModelBuilder().build();

        vehicleRepository.save(model);

        final var modelJPA = modelJPARepository.findById(model.name());
        final var brandJPA = brandJPARepository.findById(model.getBrandName());
        Assertions.assertThat(modelJPA.isPresent()).isTrue();
        Assertions.assertThat(brandJPA.isPresent()).isTrue();
    }

    @Test
    void mustInsertAVehicle() {
        final var vehicle = new VehicleBuilder().build();

        vehicleRepository.save(vehicle);

        final var vehicleJPA = vehicleJPARepository.findById(vehicle.plateNumber());
        Assertions.assertThat(vehicleJPA.isPresent()).isTrue();
    }

    @Test
    void mustInsertABrandAndAModelWhenInsertAVehicle() {
        final var vehicle = new VehicleBuilder().build();

        vehicleRepository.save(vehicle);

        final var vehicleJPA = vehicleJPARepository.findById(vehicle.plateNumber());
        final var modelJPA = modelJPARepository.findById(vehicle.getModelName());
        final var brandJPA = brandJPARepository.findById(vehicle.getBrandName());
        Assertions.assertThat(vehicleJPA.isPresent()).isTrue();
        Assertions.assertThat(modelJPA.isPresent()).isTrue();
        Assertions.assertThat(brandJPA.isPresent()).isTrue();
    }

    @Test
    void mustFindABrandByName() {
        final var brand = new BrandBuilder().build();
        vehicleRepository.save(brand);

        final var brandRetrivedByName = vehicleRepository.findBrandByName(brand.name()).orElseThrow();

        Assertions.assertThat(brandRetrivedByName.name()).isEqualTo(brand.name());
    }

    @Test
    void mustFindAModelByNameAndBrandName() {
        final var model = new ModelBuilder().build();
        vehicleRepository.save(model);

        final var modelRetrived = vehicleRepository
                .findModelByNameAndBrandName(model.name(), model.getBrandName())
                .orElseThrow();

        Assertions.assertThat(modelRetrived.name()).isEqualTo(model.name());
        Assertions.assertThat(modelRetrived.getBrandName()).isEqualTo(model.getBrandName());
    }

    @Test
    void mustFindAVehicleByPlateNumber() {
        final var vehicle = new VehicleBuilder().build();
        vehicleRepository.save(vehicle);

        final var vehicleRetrived = vehicleRepository.findVehicleByPlateNumber(vehicle.plateNumber()).orElseThrow();

        Assertions.assertThat(vehicleRetrived.plateNumber()).isEqualTo(vehicle.plateNumber());
        Assertions.assertThat(vehicleRetrived.getBrandName()).isEqualTo(vehicle.getBrandName());
        Assertions.assertThat(vehicleRetrived.getModelName()).isEqualTo(vehicle.getModelName());
    }
}
