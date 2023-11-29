package com.emanuelvictor.api.functional.bertosimulators.domain.entities;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VehicleTests {

    @Test
    void mustCreateAInstanceOfVehicle() {
        final String plateNumber = "EFR2003";
        final Vehicle vehicle = new Vehicle(plateNumber);

        Assertions.assertThat(vehicle.getPlateNumber()).isEqualTo(plateNumber);
    }
}
