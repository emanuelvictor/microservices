package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.aid.AbstractDeleteCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class DeleteVehicleImpl extends AbstractDeleteCommand<Vehicle, VehicleCommandInput> implements DeleteVehicle {
}
