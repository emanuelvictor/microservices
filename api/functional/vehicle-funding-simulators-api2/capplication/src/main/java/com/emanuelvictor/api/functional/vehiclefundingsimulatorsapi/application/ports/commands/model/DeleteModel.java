package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.model;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.aid.DeleteCommand;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.models.delete','root.vehicle-funding-simulators.models','root.vehicle-funding-simulators','root')")
public interface DeleteModel extends DeleteCommand<ModelCommandInput> {

}