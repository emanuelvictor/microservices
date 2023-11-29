package com.emanuelvictor.api.functional.bertosimulators.domain.ports.simulators;

import com.emanuelvictor.api.functional.bertosimulators.domain.entities.Simulation;

public interface Simulator {

    void execute(Simulation simulation);
}
