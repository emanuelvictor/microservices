package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.steps.pan;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.Simulation;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.steps.AbstractStep;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.browser.BrowserInstance;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChoiceCarAsTypeOfVehicle extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;

    private final PanStep continueToFillSimulationData;
    @Override
    public void execute(Simulation simulation) {
        logger.info("choiceCarAsTypeOfVehicle");
        final List<WebElement> elements = browserInstance.getElementsByClassName("pan-mahoe-card");
        if (verifyElements(elements)) elements.get(0).click();
        continueToFillSimulationData.execute(simulation);
    }
}
