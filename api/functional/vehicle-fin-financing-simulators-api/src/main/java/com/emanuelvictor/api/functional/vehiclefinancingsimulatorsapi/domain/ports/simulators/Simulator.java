package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.ports.simulators;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.entities.Simulation;

public interface Simulator {

    void execute(Simulation simulation);
}
