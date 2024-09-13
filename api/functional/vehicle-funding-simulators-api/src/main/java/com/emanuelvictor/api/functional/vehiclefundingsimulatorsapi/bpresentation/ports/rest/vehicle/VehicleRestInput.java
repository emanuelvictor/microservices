package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.model.ModelRestInput;

public record VehicleRestInput(String plateNumber, ModelRestInput model) {
}
