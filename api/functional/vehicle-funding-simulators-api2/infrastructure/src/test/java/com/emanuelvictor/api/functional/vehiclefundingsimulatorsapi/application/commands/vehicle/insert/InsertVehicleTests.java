package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle.insert;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.SpringBootTests;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle.VehicleCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle.VehicleCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle.insert.InsertVehicle;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.builders.VehicleBuilder;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.jpa.VehicleJPA;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.jpa.VehicleJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InsertVehicleTests extends SpringBootTests {

    @Autowired
    private InsertVehicle insertVehicle;

    @Autowired
    private VehicleJPARepository vehicleJPARepository;

    @Test
    void mustInsertANewModel() {
        final String brand = "Brand";
        final String model = "Model";
        final String plateNumber = VehicleBuilder.generatePlateNumber();
        final BrandCommandInput brandCommandInput = new BrandCommandInput(brand);
        final ModelCommandInput modelCommandInput = new ModelCommandInput(model, brandCommandInput);
        final VehicleCommandInput vehicleCommandInput = new VehicleCommandInput(plateNumber, modelCommandInput);

        final VehicleCommandOutput vehicleCommandOutput = insertVehicle.execute(vehicleCommandInput);

        Assertions.assertThat(vehicleCommandOutput.plateNumber()).isEqualTo(plateNumber);
        final VehicleJPA vehicleJPA = vehicleJPARepository.findById(plateNumber).orElseThrow();
        Assertions.assertThat(vehicleJPA).usingRecursiveComparison().isEqualTo(vehicleCommandOutput);
        Assertions.assertThat(vehicleJPA).usingRecursiveComparison().isEqualTo(vehicleCommandInput);
    }
}
