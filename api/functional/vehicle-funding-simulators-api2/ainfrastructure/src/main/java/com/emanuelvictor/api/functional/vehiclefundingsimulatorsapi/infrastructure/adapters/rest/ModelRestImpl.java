package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.rest;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.rest.aid.AbstractRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.model.ModelRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.model.ModelRestInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.model.ModelRestOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.ModelCommandOutput;
import org.springframework.stereotype.Service;

@Service
public class ModelRestImpl extends AbstractRest<ModelCommandInput, ModelCommandOutput, ModelRestInput, ModelRestOutput> implements ModelRest {
}
