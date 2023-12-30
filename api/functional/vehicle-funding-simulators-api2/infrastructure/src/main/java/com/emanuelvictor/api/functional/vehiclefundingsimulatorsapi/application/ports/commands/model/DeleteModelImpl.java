package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.model;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.aid.AbstractDeleteCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Model;
import org.springframework.stereotype.Service;

@Service
public class DeleteModelImpl extends AbstractDeleteCommand<Model, ModelCommandInput> implements DeleteModel {
}
