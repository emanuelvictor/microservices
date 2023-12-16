package com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.model.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.model.vehicle.Brand;
import com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.model.vehicle.Model;
import com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.model.vehicle.Vehicle;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VehicleTests {

    @Test
    void mustCreateAInstanceOfVehicle() {
        final String brandName = "Wolks";
        final Brand brand = new BrandBuilder().name(brandName).build();
        final String modelName = "Gol";
        final Model model = new ModelBuilder().brand(brand).name(modelName).build();
        final String plateNumber = "JVO6879";

        final Vehicle vehicle = new VehicleBuilder()
                .plateNumber(plateNumber)
                .model(model)
                .build();

        Assertions.assertThat(vehicle.plateNumber()).isEqualTo(plateNumber);
        Assertions.assertThat(vehicle.getModelName()).isEqualTo(modelName);
        Assertions.assertThat(vehicle.getBrandName()).isEqualTo(brandName);
    }
}
