package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.model;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand.BrandCommandOutput;

public record ModelCommandOutput(String name, BrandCommandOutput brand) {
}
