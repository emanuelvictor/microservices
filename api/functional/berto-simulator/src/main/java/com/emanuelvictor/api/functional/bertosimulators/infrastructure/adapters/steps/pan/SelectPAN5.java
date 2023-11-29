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
public class SelectPAN5 extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;
    private final PanStep waitUntilToButtonRecalcIsShowing;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("selectPAN5");
        List<WebElement> elements = browserInstance.getDriver().findElements(By.id("pan-mahoe-select-9"));
        if (verifyElements(elements)) {
            elements.get(0).click();
            elements = browserInstance.getDriver().findElements(By.className("pan-mahoe-select-option--show-in-filter"));
            if (verifyElements(elements)) {
                elements.get(elements.size() - 1).click();
            }
        }
        waitUntilToButtonRecalcIsShowing.execute(simulation);
    }

}
