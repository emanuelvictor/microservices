package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.rest;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.rest.aid.AbstractRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.vehicle.VehicleRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.vehicle.VehicleRestInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.vehicle.VehicleRestOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class VehicleRestImpl extends AbstractRest<Vehicle, VehicleRestInput, VehicleRestOutput> implements VehicleRest {
}
