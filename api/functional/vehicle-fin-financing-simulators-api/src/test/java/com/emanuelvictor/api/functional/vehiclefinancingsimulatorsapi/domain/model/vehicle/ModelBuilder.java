package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle;

public class ModelBuilder {

    private String name;
    private Brand brand;

    public ModelBuilder() {
        this.name = "GOL";
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
