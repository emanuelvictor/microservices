package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.SpringBootTests;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.VehicleBuilder;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa.VehicleJPARepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.VehicleRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;


public class VehicleRepositoryImplTests extends SpringBootTests {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleJPARepository vehicleJPARepository;

    @Test
    void mustInsertAVehicle() {
        final var vehicle = new VehicleBuilder().build();

        vehicleRepository.create(vehicle);

        final var vehicleJPA = vehicleJPARepository.findById(vehicle.plateNumber());
        Assertions.assertThat(vehicleJPA.isPresent()).isTrue();
    }

    @Test
    void mustFindAVehicleByPlateNumber() {
        final var vehicle = new VehicleBuilder().build();
        vehicleRepository.create(vehicle);

        final var vehicleRetrived = vehicleRepository.findVehicleByPlateNumber(vehicle.plateNumber()).orElseThrow();

        Assertions.assertThat(vehicleRetrived.plateNumber()).isEqualTo(vehicle.plateNumber());
        Assertions.assertThat(vehicleRetrived.getBrandName()).isEqualTo(vehicle.getBrandName());
        Assertions.assertThat(vehicleRetrived.getModelName()).isEqualTo(vehicle.getModelName());
    }

    @Test
    void mustGetAPageOfVehicleFilteredByBrandName() {
        final var brand = "brand";
        final var brandName = brand + "Name";
        final var countOfResultsExpected = 3;
        for (int i = 0; i < 15; i++) {
            final var vehicle = new VehicleBuilder()
                    .brandName((i < countOfResultsExpected) ? brandName : UUID.randomUUID().toString())
                    .build();
            vehicleRepository.create(vehicle);
        }

        final var pageOfVehiclesFilteredByBrandName = vehicleRepository
                .getAPageOfVehiclesFromFilters(brand, PageRequest.of(0, 5));

        Assertions.assertThat(pageOfVehiclesFilteredByBrandName.getContent().size()).isEqualTo(countOfResultsExpected);
        Assertions.assertThat(pageOfVehiclesFilteredByBrandName.getContent()).extracting(Vehicle::getBrandName)
                .contains(brandName);
    }

    @Test
    void mustGetAPageOfVehicleFilteredByModelName() {
        final var model = "model";
        final var modelName = model + "Name";
        final var countOfResultsExpected = 3;
        for (int i = 0; i < 15; i++) {
            final var vehicle = new VehicleBuilder()
                    .modelName((i < countOfResultsExpected) ? modelName : UUID.randomUUID().toString())
                    .build();
            vehicleRepository.create(vehicle);
        }

        final var pageOfVehiclesFilteredByBrandName = vehicleRepository
                .getAPageOfVehiclesFromFilters(model, PageRequest.of(0, 5));

        Assertions.assertThat(pageOfVehiclesFilteredByBrandName.getContent().size()).isEqualTo(countOfResultsExpected);
        Assertions.assertThat(pageOfVehiclesFilteredByBrandName.getContent()).extracting(Vehicle::getModelName)
                .contains(modelName);
    }

    @Test
    void mustGetAPageOfVehicleFilteredByPlateNumber() {
        final var halfPlateNumber = "123";
        final var countOfResultsExpected = 3;
        for (int i = 0; i < 15; i++) {
            final var vehicle = new VehicleBuilder()
                    .plateNumber((i < countOfResultsExpected) ? VehicleBuilder.generatePlateNumber(halfPlateNumber) : VehicleBuilder.generatePlateNumber())
                    .build();
            vehicleRepository.create(vehicle);
        }

        final var pageOfVehiclesFilteredByBrandName = vehicleRepository
                .getAPageOfVehiclesFromFilters(halfPlateNumber, PageRequest.of(0, 5));

        Assertions.assertThat(pageOfVehiclesFilteredByBrandName.getContent().size()).isEqualTo(countOfResultsExpected);
        final Predicate<List<? extends String>> predicate = plateNumbers -> plateNumbers.stream().allMatch(s -> s.contains(halfPlateNumber));
        Assertions.assertThat(pageOfVehiclesFilteredByBrandName.getContent()).extracting(Vehicle::plateNumber)
                .is(new Condition<>(Objects.requireNonNull(predicate), "Verify if all plate numbers retrived contains half plate number"));
    }
}
