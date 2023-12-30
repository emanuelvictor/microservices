package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.rest;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.rest.aid.AbstractRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.vehicle.VehicleRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.vehicle.VehicleRestInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.vehicle.VehicleRestOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.vehicle.VehicleCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.vehicle.VehicleCommandOutput;
import org.springframework.stereotype.Service;

@Service
public class VehicleRestImpl extends AbstractRest<VehicleCommandInput, VehicleCommandOutput, VehicleRestInput, VehicleRestOutput> implements VehicleRest {
}
