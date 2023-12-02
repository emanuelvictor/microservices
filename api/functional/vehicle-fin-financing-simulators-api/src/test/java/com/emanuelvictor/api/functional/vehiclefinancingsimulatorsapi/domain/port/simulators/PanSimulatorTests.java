package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.port.simulators;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.AbstractIntegrationTests;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.entities.Customer;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.entities.Simulation;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.entities.Vehicle;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.ports.simulators.Simulator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PanSimulatorTests extends AbstractIntegrationTests {

    @Autowired
    private Simulator panSimulator;

    @Test
    void mustExecutePanSimulation() {
        final String document = "05920838531";
        final String contact = "45978944646";
        final Customer customer = new Customer(document, contact);
        final String plateNumber = "JVO6879";
        final Vehicle vehicle = new Vehicle(plateNumber);
        final String cotation = "20000";
        final String requestedEntry = "5000";
        final Simulation simulation = new Simulation(cotation, customer, requestedEntry, vehicle);

        panSimulator.execute(simulation);
    }

}
