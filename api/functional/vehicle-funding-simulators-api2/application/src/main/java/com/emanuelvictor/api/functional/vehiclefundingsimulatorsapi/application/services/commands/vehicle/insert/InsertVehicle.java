package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.vehicle.insert;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.InsertCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.vehicle.VehicleCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.vehicle.VehicleCommandOutput;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.vehicles.post','root.vehicle-funding-simulators.vehicles','root.vehicle-funding-simulators','root')")
public interface InsertVehicle extends InsertCommand<VehicleCommandInput, VehicleCommandOutput> {

}