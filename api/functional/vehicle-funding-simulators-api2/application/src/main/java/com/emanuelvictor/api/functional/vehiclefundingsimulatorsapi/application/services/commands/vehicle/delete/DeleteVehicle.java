package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.vehicle.delete;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.DeleteCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.vehicle.VehicleCommandInput;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.vehicles.delete','root.vehicle-funding-simulators.vehicles','root.vehicle-funding-simulators','root')")
public interface DeleteVehicle extends DeleteCommand<VehicleCommandInput> {

}