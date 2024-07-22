package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle.insert;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.InsertCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle.VehicleCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle.VehicleCommandOutput;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.vehicles.post','root.vehicle-funding-simulators.vehicles','root.vehicle-funding-simulators','root')")
public interface InsertVehicle extends InsertCommand<VehicleCommandInput, VehicleCommandOutput> {

}