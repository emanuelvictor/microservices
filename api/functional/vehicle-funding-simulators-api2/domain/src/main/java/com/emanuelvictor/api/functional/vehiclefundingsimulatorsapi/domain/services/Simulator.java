package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.services;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.simulation.Simulation;

public interface Simulator {

    void execute(Simulation simulation);
}
