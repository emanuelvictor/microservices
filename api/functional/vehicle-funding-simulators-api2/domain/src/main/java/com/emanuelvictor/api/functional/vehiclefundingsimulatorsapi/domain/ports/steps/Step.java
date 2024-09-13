package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.steps;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.simulation.Simulation;

public interface Step {

    void execute(Simulation simulation);

}
