package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.steps.pan;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.Simulation;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.steps.AbstractStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.browser.BrowserInstance;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        logger.info("waitUntilToButtonRecalcIsShowing");
        final Wait<WebDriver> wait = new WebDriverWait(browserInstance.getDriver(), Duration.ofSeconds(1000), Duration.ofSeconds(2));
        wait.until(d -> {
            final List<WebElement> elements = browserInstance.getElementsByTagName("button");
            return elements.stream().map(webElement -> {
                        return webElement.getAttribute("label") != null &&
                                webElement.getAttribute("label").equals("Recalcular") &&
                                (webElement.getAttribute("disabled") == null ||
                                        Objects.equals(webElement.getAttribute("disabled"), "false"));
                    })
                    .anyMatch(aBoolean -> aBoolean.equals(true));
        });
        clickOnRecalcButton.execute(simulation);
    }

}
