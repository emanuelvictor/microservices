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
public class TryLogin extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;

    private final PanStep waitUntilDocumentFieldIsShowing;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("tryLogin");
        final List<WebElement> username = browserInstance.getDriver().findElements(By.id("login"));
        username.get(0).sendKeys("10650570910");
        final List<WebElement> password = browserInstance.getDriver().findElements(By.id("password"));
        password.get(0).sendKeys("#Berto129");
        browserInstance.getDriver().findElements(By.className("pan-mahoe-button--primary")).get(0).click();
        waitUntilDocumentFieldIsShowing.execute(simulation);
    }
}
