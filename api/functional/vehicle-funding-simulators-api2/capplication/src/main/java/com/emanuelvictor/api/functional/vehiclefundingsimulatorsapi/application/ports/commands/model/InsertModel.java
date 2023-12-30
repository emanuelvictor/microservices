package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.model;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.aid.InsertCommand;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.models.post','root.vehicle-funding-simulators.models','root.vehicle-funding-simulators','root')")
public interface InsertModel extends InsertCommand<ModelCommandInput, ModelCommandOutput> {

}