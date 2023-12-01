package com.emanuelvictor.api.functional.bertosimulators.infrastructure.adapters.steps.pan;

import com.emanuelvictor.api.functional.bertosimulators.domain.entities.Simulation;
import com.emanuelvictor.api.functional.bertosimulators.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.bertosimulators.infrastructure.adapters.steps.AbstractStep;
import com.emanuelvictor.api.functional.bertosimulators.infrastructure.browser.BrowserInstance;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FillRequestedEntry extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;

    private final PanStep waitUntilConditionPropostButtonIsShowing;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("fillRequestedEntry");
        browserInstance.getElementById("requestedEntry").sendKeys(simulation.getRequestedEntry());
        waitUntilConditionPropostButtonIsShowing.execute(simulation);
    }

}
