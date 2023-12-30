package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.model.ModelRestOutput;

public record VehicleRestOutput(String plateNumber, ModelRestOutput model) {
}
