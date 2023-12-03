package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle;

public class VehicleBuilder {

    private String plateNumber;
    private Model model;

    public VehicleBuilder() {
        this.plateNumber = "JVO6879";
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

    public Vehicle build(){
        return new Vehicle(plateNumber, model);
    }
}
