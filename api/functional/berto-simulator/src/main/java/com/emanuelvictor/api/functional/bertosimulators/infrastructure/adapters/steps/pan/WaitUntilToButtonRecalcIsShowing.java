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
public class WaitUntilToButtonRecalcIsShowing extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;
    private final PanStep clickOnRecalcButton;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("clickOnRecalcButton");
        final List<WebElement> elements = browserInstance.getDriver().findElements(By.className("pan-mahoe-button--primary"));
        if (verifyElements(elements)) elements.get(0).click();
        clickOnRecalcButton.execute(simulation);
    }

}
