package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model.ModelCommandInput;

public record VehicleCommandInput(String plateNumber, ModelCommandInput model) {
}
