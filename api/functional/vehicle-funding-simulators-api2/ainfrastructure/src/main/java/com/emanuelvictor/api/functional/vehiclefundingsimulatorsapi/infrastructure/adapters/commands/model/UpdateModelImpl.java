package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.commands.model;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.commands.aid.AbstractUpdateCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.ModelCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.UpdateModel;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Model;
import org.springframework.stereotype.Service;

@Service
public class UpdateModelImpl extends AbstractUpdateCommand<Model, ModelCommandInput, ModelCommandOutput> implements UpdateModel {
}
