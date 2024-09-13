package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle.update;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.UpdateCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle.VehicleCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle.VehicleCommandOutput;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.vehicles.put','root.vehicle-funding-simulators.vehicles','root.vehicle-funding-simulators','root')")
public interface UpdateVehicle extends UpdateCommand<VehicleCommandInput, VehicleCommandOutput> {

}