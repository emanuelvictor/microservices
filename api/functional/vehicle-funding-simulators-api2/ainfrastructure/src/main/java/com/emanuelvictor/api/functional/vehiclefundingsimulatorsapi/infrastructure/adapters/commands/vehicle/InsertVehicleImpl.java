package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.commands.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.commands.aid.AbstractInsertCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.vehicle.InsertVehicle;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.vehicle.VehicleCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.vehicle.VehicleCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class InsertVehicleImpl extends AbstractInsertCommand<Vehicle, VehicleCommandInput, VehicleCommandOutput> implements InsertVehicle {
}
