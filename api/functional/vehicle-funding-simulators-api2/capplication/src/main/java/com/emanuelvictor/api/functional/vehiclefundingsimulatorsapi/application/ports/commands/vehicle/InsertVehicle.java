package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid.InsertCommand;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.vehicles.post','root.vehicle-funding-simulators.vehicles','root.vehicle-funding-simulators','root')")
public interface InsertVehicle extends InsertCommand<VehicleCommandInput, VehicleCommandOutput> {

}