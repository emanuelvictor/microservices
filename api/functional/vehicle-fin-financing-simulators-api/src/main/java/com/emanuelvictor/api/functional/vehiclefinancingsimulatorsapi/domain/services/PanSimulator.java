package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.services;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.Simulation;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.ports.steps.pan.PanStep;

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
