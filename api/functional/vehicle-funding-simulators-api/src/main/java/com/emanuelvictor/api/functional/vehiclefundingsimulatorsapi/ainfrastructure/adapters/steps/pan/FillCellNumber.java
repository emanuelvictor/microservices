package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.steps.pan;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.Simulation;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.steps.AbstractStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.browser.BrowserInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FillCellNumber extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;

    private final PanStep fillPlateNumber;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("fillCellNumber");
        browserInstance.getElementById("cellnumber").sendKeys(simulation.getCellNumberFromCustomer());
        fillPlateNumber.execute(simulation);
    }

}
