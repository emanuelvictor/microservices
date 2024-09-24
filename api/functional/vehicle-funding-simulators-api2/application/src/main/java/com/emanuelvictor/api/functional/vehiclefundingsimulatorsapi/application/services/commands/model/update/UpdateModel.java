package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.model.update;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.UpdateCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.model.ModelCommandOutput;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.models.put','root.vehicle-funding-simulators.models','root.vehicle-funding-simulators','root')")
public interface UpdateModel extends UpdateCommand<ModelCommandInput, ModelCommandOutput> {

}