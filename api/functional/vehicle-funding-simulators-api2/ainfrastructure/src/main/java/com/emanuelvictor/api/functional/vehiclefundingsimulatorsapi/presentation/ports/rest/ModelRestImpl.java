package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.model.ModelCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.aid.AbstractRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.model.ModelRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.model.ModelRestInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.model.ModelRestOutput;
import org.springframework.stereotype.Service;

@Service
public class ModelRestImpl extends AbstractRest<ModelCommandInput, ModelCommandOutput, ModelRestInput, ModelRestOutput> implements ModelRest {
}
