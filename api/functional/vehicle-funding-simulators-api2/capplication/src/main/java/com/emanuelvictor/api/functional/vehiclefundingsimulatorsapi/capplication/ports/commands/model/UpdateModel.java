package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid.UpdateCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.ModelCommandOutput;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.models.put','root.vehicle-funding-simulators.models','root.vehicle-funding-simulators','root')")
public interface UpdateModel extends UpdateCommand<ModelCommandInput, ModelCommandOutput> {

}