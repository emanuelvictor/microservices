package com.emanuelvictor.api.functional.bertosimulators.domain.entities;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimulationTests {

    @Test
    void mustCreateAInstanceOfSimulation() {
        final String document = "07124762944";
        final String contact = "45998261540";
        final Customer customer = new Customer(document, contact);
        final String plateNumber = "EFR2003";
        final Vehicle vehicle = new Vehicle(plateNumber);
        final String cotation = "20000";
        final String requestedEntry = "5000";

        final Simulation simulation = new Simulation(cotation, customer, requestedEntry, vehicle);

        Assertions.assertThat(simulation.getCotation()).isEqualTo(cotation);
        Assertions.assertThat(simulation.getRequestedEntry()).isEqualTo(requestedEntry);
        Assertions.assertThat(simulation.getCellNumberFromCustomer()).isEqualTo(contact);
        Assertions.assertThat(simulation.getPlateNumberFromVehicle()).isEqualTo(plateNumber);
        Assertions.assertThat(simulation.getDocumentFromCustomer()).isEqualTo(document);
    }
}
