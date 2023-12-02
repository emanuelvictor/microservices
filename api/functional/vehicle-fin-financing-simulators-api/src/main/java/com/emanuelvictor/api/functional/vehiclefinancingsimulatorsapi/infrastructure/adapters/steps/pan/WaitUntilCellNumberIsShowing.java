package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.steps.pan;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.entities.Simulation;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.steps.AbstractStep;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.browser.BrowserInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitUntilCellNumberIsShowing extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;

    private final PanStep fillCellNumber;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("waitUntilCellNumberIsShowing");
        browserInstance.waitForElementsIds("cellnumber");
        fillCellNumber.execute(simulation);
    }

}
