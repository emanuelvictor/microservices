package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.commands.model;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.commands.aid.AbstractInsertCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.InsertModel;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.ModelCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Model;
import org.springframework.stereotype.Service;

@Service
public class InsertModelImpl extends AbstractInsertCommand<Model, ModelCommandInput, ModelCommandOutput> implements InsertModel {
}
