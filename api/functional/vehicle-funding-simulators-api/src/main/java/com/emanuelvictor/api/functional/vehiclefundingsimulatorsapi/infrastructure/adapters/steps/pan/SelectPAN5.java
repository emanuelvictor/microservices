package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.steps.pan;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.Simulation;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.steps.AbstractStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.browser.BrowserInstance;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SelectPAN5 extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;
    private final PanStep waitUntilToButtonRecalcIsShowing;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("selectPAN5");
        final WebElement element = browserInstance.getElementById("pan-mahoe-select-9");
        if (verifyElements(element)) {
            element.click();
            final List<WebElement> options = browserInstance
                    .getElementsByClassName("pan-mahoe-select-option--show-in-filter");
            if (verifyElements(options))
                options.get(options.size() - 1).click();
        }
        waitUntilToButtonRecalcIsShowing.execute(simulation);
    }

}
