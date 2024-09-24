package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.model.insert;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.impl.AbstractInsertCommandImpl;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.model.ModelCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Model;
import org.springframework.stereotype.Service;

@Service
public class InsertModelImpl extends AbstractInsertCommandImpl<Model, ModelCommandInput, ModelCommandOutput> implements InsertModel {
}
