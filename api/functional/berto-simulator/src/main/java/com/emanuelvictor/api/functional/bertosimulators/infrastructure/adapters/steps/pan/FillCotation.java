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
public class FillCotation extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;
    private final PanStep fillRequestedEntry;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("fillCotation");
        String cotation = simulation.getCotation();
        if (cotation == null) {
            cotation = browserInstance.getDriver().findElements(By.className("state-values__assisted")).get(0).getText();
            cotation = cotation.replace("Cotação: R$", "").replace(",00", "") + ".00";
        }
        final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("value"));
        if (verifyElements(elements)) elements.get(0).sendKeys(cotation);
        fillRequestedEntry.execute(simulation);
    }

}
