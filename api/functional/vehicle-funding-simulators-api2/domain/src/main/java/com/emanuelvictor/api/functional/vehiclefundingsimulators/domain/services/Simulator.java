package com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.services;

import com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.model.Simulation;

public interface Simulator {

    void execute(Simulation simulation);
}
