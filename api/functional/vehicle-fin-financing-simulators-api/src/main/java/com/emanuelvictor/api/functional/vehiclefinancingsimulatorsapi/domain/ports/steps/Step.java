package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.ports.steps;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.entities.Simulation;

public interface Step {

    void execute(Simulation simulation);

}
