package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.vehicle.insert;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.impl.AbstractInsertCommandImpl;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.vehicle.VehicleCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.vehicle.VehicleCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class InsertVehicleImpl extends AbstractInsertCommandImpl<Vehicle, VehicleCommandInput, VehicleCommandOutput> implements InsertVehicle {
}
