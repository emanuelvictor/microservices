package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.steps.pan;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.entities.Simulation;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.steps.AbstractStep;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.browser.BrowserInstance;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClickOnRecalcButton extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;
    private final PanStep waitUntilToButtonProceedIsShowing;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("clickOnRecalcButton");
        final List<WebElement> elements = browserInstance.getElementsByTagName("button");
        elements.forEach(webElement -> {
            if (webElement.getAttribute("label") != null &&
                    webElement.getAttribute("label").equals("Recalcular")) {
                webElement.click();
            }
        });
        waitUntilToButtonProceedIsShowing.execute(simulation);
    }

}
