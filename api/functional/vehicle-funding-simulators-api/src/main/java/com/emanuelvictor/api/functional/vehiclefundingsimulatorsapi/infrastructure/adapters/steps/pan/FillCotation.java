package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.steps.pan;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.Simulation;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.steps.AbstractStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.browser.BrowserInstance;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FillCotation extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;
    private final PanStep fillRequestedEntry;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("fillCotation");
        String cotation = simulation.getCotation();
        if (cotation == null) {
            cotation = browserInstance.getElementsByClassName(("state-values__assisted")).get(0).getText();
            cotation = cotation.replace("Cotação: R$", "").replace(",00", "") + ".00";
        }
        final WebElement element = browserInstance.getElementById("value");
        element.sendKeys(cotation);
        fillRequestedEntry.execute(simulation);
    }
}
