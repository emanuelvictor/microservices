package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.model.update;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.impl.AbstractUpdateCommandImpl;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.model.ModelCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Model;
import org.springframework.stereotype.Service;

@Service
public class UpdateModelImpl extends AbstractUpdateCommandImpl<Model, ModelCommandInput, ModelCommandOutput> implements UpdateModel {
}
