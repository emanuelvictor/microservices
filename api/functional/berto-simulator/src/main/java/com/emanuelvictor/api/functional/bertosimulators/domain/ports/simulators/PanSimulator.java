package com.emanuelvictor.api.functional.bertosimulators.domain.ports.simulators;

import com.emanuelvictor.api.functional.bertosimulators.domain.entities.Simulation;
import com.emanuelvictor.api.functional.bertosimulators.domain.ports.steps.pan.PanStep;

public class PanSimulator implements Simulator {

    private final PanStep begin;

    public PanSimulator(PanStep begin) {
        this.begin = begin;
    }

    @Override
    public void execute(final Simulation simulation) {
        begin.execute(simulation);
    }

}
