package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.steps;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.Simulation;

public interface Step {

    void execute(Simulation simulation);

}
