package com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.ports.steps;

import com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.model.Simulation;

public interface Step {

    void execute(Simulation simulation);

}
