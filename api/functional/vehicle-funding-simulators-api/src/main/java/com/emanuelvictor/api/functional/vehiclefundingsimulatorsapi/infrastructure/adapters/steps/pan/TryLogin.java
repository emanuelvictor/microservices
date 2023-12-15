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
public class TryLogin extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;

    private final PanStep waitUntilDocumentFieldIsShowing;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("tryLogin");
        browserInstance.waitFor(4);
        final WebElement usernameInput = browserInstance.getElementById("login");
        usernameInput.sendKeys("10650570910");
        final WebElement passwordInput = browserInstance.getElementById("password");
        passwordInput.sendKeys("#Berto129");
        browserInstance.getElementsByClassName("pan-mahoe-button--primary").get(0).click();
        waitUntilDocumentFieldIsShowing.execute(simulation);
    }
}
