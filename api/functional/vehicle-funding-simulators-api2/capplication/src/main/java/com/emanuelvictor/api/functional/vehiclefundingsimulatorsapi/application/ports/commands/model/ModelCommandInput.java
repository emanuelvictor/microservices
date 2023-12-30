package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand.BrandCommandInput;

public record ModelCommandInput(String name, BrandCommandInput brand) {
}
