package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.services;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.Simulation;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.steps.pan.PanStep;

//  TODO Deve ir para infrastructure?
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
