package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.steps.pan;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.Simulation;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.steps.AbstractStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.browser.BrowserInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FillPlateNumber extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;

    private final PanStep waitUntilCotationFieldIsShowing;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("fillPlateNumber");
        browserInstance.getElementById("plate").sendKeys(simulation.getPlateNumberFromVehicle());
        waitUntilCotationFieldIsShowing.execute(simulation);
    }

}
