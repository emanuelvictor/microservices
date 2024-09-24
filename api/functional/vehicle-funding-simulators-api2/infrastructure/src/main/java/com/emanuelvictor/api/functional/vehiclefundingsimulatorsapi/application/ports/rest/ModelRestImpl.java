package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.rest;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.rest.aid.AbstractRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.model.ModelRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.model.ModelRestInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.model.ModelRestOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Model;
import org.springframework.stereotype.Service;

@Service
public class ModelRestImpl extends AbstractRest<Model, ModelRestInput, ModelRestOutput> implements ModelRest {
}
