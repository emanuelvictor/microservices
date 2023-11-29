package com.emanuelvictor.api.functional.bertosimulators.domain.ports.steps;

import com.emanuelvictor.api.functional.bertosimulators.domain.entities.Simulation;

public interface Step {

    void execute(Simulation simulation);

}
