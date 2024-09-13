package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.builders;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;

import java.util.UUID;

public class BrandBuilder {

    private String name;

    public BrandBuilder() {
        this.name = UUID.randomUUID().toString();
    }

    public BrandBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public Brand build() {
        return new Brand(name);
    }
}
