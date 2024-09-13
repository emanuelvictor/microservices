package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.steps.pan;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.Simulation;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.steps.AbstractStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.browser.BrowserInstance;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContinueToFillSimulationData extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;

    private final PanStep waitUntilCellNumberIsShowing;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("continueToFillSimulationData");
        final List<WebElement> elements = browserInstance.getElementsByClassName("mahoe-button");
        if (verifyElements(elements)) elements.get(0).click();
        waitUntilCellNumberIsShowing.execute(simulation);
    }

}
