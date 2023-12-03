package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.services;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.SpringBootTests;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.*;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle.Brand;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle.Model;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PanSimulatorTests extends SpringBootTests {

    @Autowired
    private Simulator panSimulator;

    @Test
    void mustExecutePanSimulation() {
        final String brandName = "Wolks";
        final Brand brand = new Brand(brandName);
        final String modelName = "Gol";
        final Model model = new Model(modelName, brand);
        final String plateNumber = "JVO6879";
        final Vehicle vehicle = new Vehicle(plateNumber, model);
        final String document = "05920838531";
        final String contact = "45978944646";
        final Customer customer = new Customer(document, contact);
        final String cotation = "20000";
        final String requestedEntry = "5000";
        final Simulation simulation = new Simulation(cotation, customer, requestedEntry, vehicle);

        panSimulator.execute(simulation);
    }

}
