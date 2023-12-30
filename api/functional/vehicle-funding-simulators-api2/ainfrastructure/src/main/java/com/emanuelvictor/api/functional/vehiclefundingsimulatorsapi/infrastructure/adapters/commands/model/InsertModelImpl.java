package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.commands.model;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.commands.aid.AbstractInsertCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.model.InsertModel;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.model.ModelCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Model;
import org.springframework.stereotype.Service;

@Service
public class InsertModelImpl extends AbstractInsertCommand<Model, ModelCommandInput, ModelCommandOutput> implements InsertModel {
}
