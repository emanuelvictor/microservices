package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.vehicle.update;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.impl.AbstractUpdateCommandImpl;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.vehicle.VehicleCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.vehicle.VehicleCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class UpdateVehicleImpl extends AbstractUpdateCommandImpl<Vehicle, VehicleCommandInput, VehicleCommandOutput> implements UpdateVehicle {
}
