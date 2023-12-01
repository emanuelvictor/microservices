package com.emanuelvictor.api.functional.bertosimulators.infrastructure.adapters.steps.pan;

import com.emanuelvictor.api.functional.bertosimulators.domain.entities.Simulation;
import com.emanuelvictor.api.functional.bertosimulators.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.bertosimulators.infrastructure.adapters.steps.AbstractStep;
import com.emanuelvictor.api.functional.bertosimulators.infrastructure.browser.BrowserInstance;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Objects;



@Component
@RequiredArgsConstructor
public class WaitUntilToButtonRecalcIsShowing extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;
    private final PanStep clickOnRecalcButton;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("clickOnRecalcButton");
        final List<WebElement> elements = browserInstance.getElementsByTagName("button");
        elements.forEach(webElement -> {
            if (webElement.getAttribute("label") != null &&
                    webElement.getAttribute("label").equals("Recalcular")) {
                final Actions actions = new Actions(browserInstance.getDriver());
                actions.moveToElement(webElement).click().perform();
            }
        });
        clickOnRecalcButton.execute(simulation);
    }

}
