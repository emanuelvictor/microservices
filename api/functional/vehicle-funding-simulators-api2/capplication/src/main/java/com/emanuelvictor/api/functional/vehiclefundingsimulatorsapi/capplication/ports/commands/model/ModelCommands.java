package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid.Commands;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.models.post','root.vehicle-funding-simulators.models','root.vehicle-funding-simulators','root')")
public interface ModelCommands extends Commands<ModelCommandInput, ModelCommandOutput> {

}