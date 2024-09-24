package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.rest;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.vehicle.VehicleCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.vehicle.VehicleCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.rest.aid.AbstractRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.vehicle.VehicleRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.vehicle.VehicleRestInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.vehicle.VehicleRestOutput;
import org.springframework.stereotype.Service;

@Service
public class VehicleRestImpl extends AbstractRest<VehicleCommandInput, VehicleCommandOutput, VehicleRestInput, VehicleRestOutput> implements VehicleRest {
}
