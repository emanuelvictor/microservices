package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.simulation;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.customer.Customer;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;

public class Simulation {

    private String cotation;
    private Customer customer;
    private String requestedEntry;
    private Vehicle vehicle;

    public Simulation(String cotation, Customer customer, String requestedEntry, Vehicle vehicle) {
        this.cotation = cotation;
        this.customer = customer;
        this.requestedEntry = requestedEntry;
        this.vehicle = vehicle;
    }

    public String getDocumentFromCustomer() {
        return customer.getDocument();
    }

    public String getCellNumberFromCustomer() {
        return customer.getContact();
    }

    public String getRequestedEntry() {
        return requestedEntry;
    }

    public String getPlateNumberFromVehicle() {
        return vehicle.plateNumber();
    }

    public String getCotation() {
        return cotation;
    }

}
