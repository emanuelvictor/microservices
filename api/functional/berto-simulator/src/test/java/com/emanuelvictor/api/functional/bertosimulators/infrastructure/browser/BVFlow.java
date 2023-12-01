package com.emanuelvictor.api.functional.bertosimulators.infrastructure.browser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BVFlow {

    private BrowserInstance browserInstance;

    @BeforeEach
    public void setUp() {
//        final BrowserOptions browserOptions = BrowserOptions.create("c158dde2915f2eeb6bbf38b273020fad", "http://localhost:34883");
//        browserInstance = BrowserInstance.openCreated(browserOptions);
        browserInstance = BrowserInstance.createNew("https://parceiro.bv.com.br/spa-ppar-base-dealer-veiculo/aprovometro/simulacao/dados-do-cliente/");
    }

    @Test
    void flowOfBVBank() {
        goToBegin();
        waitUntilLoginFieldIsShowing();
        tryLogin();
    }

    private void goToBegin() {
        System.out.println("goToBegin");
        browserInstance.goTo("https://parceiro.bv.com.br/spa-ppar-base-dealer-veiculo/aprovometro/simulacao/dados-do-cliente/");
    }

    private void waitUntilLoginFieldIsShowing() {
        browserInstance.waitFor(Duration.ofSeconds(4));
        System.out.println("waitUntilLoginFieldIsShowing");
        browserInstance.waitForElementsIds("mat-input-0");
    }

    private void tryLogin() {
        System.out.println("tryLogin");
        browserInstance.getElementById("mat-input-0").sendKeys("p432508.6911");
        browserInstance.getElementById("mat-input-1").sendKeys("15470707");
        browserInstance.getElementsByTagName("button").get(0).click();
    }
}
