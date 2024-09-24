package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.model.insert;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.InsertCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.model.ModelCommandOutput;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.models.post','root.vehicle-funding-simulators.models','root.vehicle-funding-simulators','root')")
public interface InsertModel extends InsertCommand<ModelCommandInput, ModelCommandOutput> {

}