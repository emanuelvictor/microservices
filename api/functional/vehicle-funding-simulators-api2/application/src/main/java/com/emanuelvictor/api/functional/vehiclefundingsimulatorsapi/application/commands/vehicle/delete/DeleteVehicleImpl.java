package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle.delete;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.impl.AbstractDeleteCommandImpl;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle.VehicleCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class DeleteVehicleImpl extends AbstractDeleteCommandImpl<Vehicle, VehicleCommandInput> implements DeleteVehicle {
}
