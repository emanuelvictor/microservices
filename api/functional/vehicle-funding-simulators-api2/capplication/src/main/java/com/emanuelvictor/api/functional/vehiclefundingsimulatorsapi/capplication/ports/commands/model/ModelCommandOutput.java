package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.model;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand.BrandCommandOutput;

public record ModelCommandOutput(String name, BrandCommandOutput brand) {
}
