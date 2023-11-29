package com.emanuelvictor.api.functional.bertosimulators.infrastructure.adapters.steps.pan;

import com.emanuelvictor.api.functional.bertosimulators.domain.entities.Simulation;
import com.emanuelvictor.api.functional.bertosimulators.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.bertosimulators.infrastructure.adapters.steps.AbstractStep;
import com.emanuelvictor.api.functional.bertosimulators.infrastructure.browser.BrowserInstance;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WaitUntilCellNumberIsShowing extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;

    private final PanStep fillCellNumber;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("waitUntilCellNumberIsShowing");
        final Wait<WebDriver> wait = new WebDriverWait(browserInstance.getDriver(), Duration.ofSeconds(60));
        wait.until(d -> {
            final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("cellnumber"));
            return verifyElements(elements);
        });
        fillCellNumber.execute(simulation);
    }

}
