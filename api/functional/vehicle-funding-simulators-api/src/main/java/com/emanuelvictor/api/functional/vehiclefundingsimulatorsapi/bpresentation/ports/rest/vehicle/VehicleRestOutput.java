package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.model.ModelRestOutput;

public record VehicleRestOutput(String plateNumber, ModelRestOutput model) {
}
