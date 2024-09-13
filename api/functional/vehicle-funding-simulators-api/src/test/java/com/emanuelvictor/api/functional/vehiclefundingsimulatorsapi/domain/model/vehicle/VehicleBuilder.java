package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle;

import java.util.Random;

public class VehicleBuilder {

    private String plateNumber;
    private Model model;

    public VehicleBuilder() {
        this.plateNumber = generatePlateNumber();
        this.model = new ModelBuilder().build();
    }

    public VehicleBuilder plateNumber(final String plateNumber) {
        this.plateNumber = plateNumber;
        return this;
    }

    public VehicleBuilder model(final Model model) {
        this.model = model;
        return this;
    }

    public VehicleBuilder modelName(final String modelName) {
        this.model = new ModelBuilder().name(modelName).build();
        return this;
    }

    public VehicleBuilder brandName(final String brandName) {
        this.model = new ModelBuilder()
                .brand(new BrandBuilder().name(brandName).build())
                .build();
        return this;
    }

    public Vehicle build() {
        return new Vehicle(plateNumber, model);
    }


    public static String generatePlateNumber(final String halfPlateNumber) {
        return halfPlateNumber + new Random().nextInt(999);
    }

    public static String generatePlateNumber() {
        return Integer.toString(new Random().nextInt(999999));
    }
}
