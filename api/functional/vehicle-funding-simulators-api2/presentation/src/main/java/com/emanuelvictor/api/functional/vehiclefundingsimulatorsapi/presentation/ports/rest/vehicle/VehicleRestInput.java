package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.model.ModelRestInput;

public record VehicleRestInput(String plateNumber, ModelRestInput model) {
}
