package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.model.delete;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.DeleteCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.model.ModelCommandInput;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.models.delete','root.vehicle-funding-simulators.models','root.vehicle-funding-simulators','root')")
public interface DeleteModel extends DeleteCommand<ModelCommandInput> {

}