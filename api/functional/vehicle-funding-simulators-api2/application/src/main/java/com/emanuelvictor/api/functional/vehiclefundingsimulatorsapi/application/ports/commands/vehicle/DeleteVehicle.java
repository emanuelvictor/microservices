package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.aid.DeleteCommand;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.vehicles.delete','root.vehicle-funding-simulators.vehicles','root.vehicle-funding-simulators','root')")
public interface DeleteVehicle extends DeleteCommand<VehicleCommandInput> {

}