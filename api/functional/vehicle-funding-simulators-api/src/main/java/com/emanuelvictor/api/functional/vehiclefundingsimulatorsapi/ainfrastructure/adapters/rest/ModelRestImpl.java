package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.rest;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.rest.aid.AbstractRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.brand.BrandRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.brand.BrandRestInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.brand.BrandRestOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.model.ModelRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.model.ModelRestInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.model.ModelRestOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand.BrandCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.ModelCommandOutput;
import org.springframework.stereotype.Service;

@Service
public class ModelRestImpl extends AbstractRest<ModelCommandInput, ModelCommandOutput, ModelRestInput, ModelRestOutput> implements ModelRest {
}
