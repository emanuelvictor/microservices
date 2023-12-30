package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.aid.AbstractUpdateCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class UpdateVehicleImpl extends AbstractUpdateCommand<Vehicle, VehicleCommandInput, VehicleCommandOutput> implements UpdateVehicle {
}
