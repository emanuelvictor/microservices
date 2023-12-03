package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle;

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
