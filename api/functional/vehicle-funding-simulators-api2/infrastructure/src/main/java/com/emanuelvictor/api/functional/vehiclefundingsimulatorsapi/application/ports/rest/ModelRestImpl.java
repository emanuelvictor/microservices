package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.rest;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.model.ModelCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.rest.aid.AbstractRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.model.ModelRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.model.ModelRestInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.model.ModelRestOutput;
import org.springframework.stereotype.Service;

@Service
public class ModelRestImpl extends AbstractRest<ModelCommandInput, ModelCommandOutput, ModelRestInput, ModelRestOutput> implements ModelRest {
}
