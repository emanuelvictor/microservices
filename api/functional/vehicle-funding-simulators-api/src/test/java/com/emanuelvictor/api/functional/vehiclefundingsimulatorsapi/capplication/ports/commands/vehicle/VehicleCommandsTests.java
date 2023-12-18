package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.SpringBootTests;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa.ModelJPA;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa.ModelJPARepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa.VehicleJPA;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa.VehicleJPARepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid.Commands;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.ModelCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.VehicleBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class VehicleCommandsTests extends SpringBootTests {


    @Autowired
    private VehicleCommands commands;

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

        final VehicleCommandOutput vehicleCommandOutput = commands.create(vehicleCommandInput);

        Assertions.assertThat(vehicleCommandOutput.plateNumber()).isEqualTo(plateNumber);
        final VehicleJPA vehicleJPA = vehicleJPARepository.findById(plateNumber).orElseThrow();
        Assertions.assertThat(vehicleJPA).usingRecursiveComparison().isEqualTo(vehicleCommandOutput);
        Assertions.assertThat(vehicleJPA).usingRecursiveComparison().isEqualTo(vehicleCommandInput);
    }
}
