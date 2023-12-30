package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.ModelCommandOutput;

public record VehicleCommandOutput(String plateNumber, ModelCommandOutput model) {
}
