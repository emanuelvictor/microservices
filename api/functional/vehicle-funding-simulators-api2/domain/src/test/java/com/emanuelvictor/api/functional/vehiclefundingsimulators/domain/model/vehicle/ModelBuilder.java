package com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.model.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.model.vehicle.Brand;
import com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.model.vehicle.Model;

import java.util.UUID;

public class ModelBuilder {

    private String name;
    private Brand brand;

    public ModelBuilder() {
        this.name = UUID.randomUUID().toString();
        this.brand = new BrandBuilder().build();
    }

    public ModelBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public ModelBuilder brand(final Brand brand) {
        this.brand = brand;
        return this;
    }

    public Model build() {
        return new Model(name, brand);
    }
}
