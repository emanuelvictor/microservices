package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.model.delete;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.impl.AbstractDeleteCommandImpl;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.model.ModelCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Model;
import org.springframework.stereotype.Service;

@Service
public class DeleteModelImpl extends AbstractDeleteCommandImpl<Model, ModelCommandInput> implements DeleteModel {
}
