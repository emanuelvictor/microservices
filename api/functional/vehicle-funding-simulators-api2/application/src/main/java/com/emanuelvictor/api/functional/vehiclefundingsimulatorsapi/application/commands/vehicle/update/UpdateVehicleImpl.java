package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle.update;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.impl.AbstractUpdateCommandImpl;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle.VehicleCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle.VehicleCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class UpdateVehicleImpl extends AbstractUpdateCommandImpl<Vehicle, VehicleCommandInput, VehicleCommandOutput> implements UpdateVehicle {
}
