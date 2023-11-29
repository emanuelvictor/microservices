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
        browserInstance.getDriver().get("https://parceiro.bv.com.br/spa-ppar-base-dealer-veiculo/aprovometro/simulacao/dados-do-cliente/");
    }

    private void waitUntilLoginFieldIsShowing() {
        System.out.println("waitUntilLoginFieldIsShowing");
        final Wait<WebDriver> wait = new WebDriverWait(browserInstance.getDriver(), Duration.ofSeconds(60));
        wait.until(d -> {
            final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("mat-input-0"));
            return verifyElements(elements);
        });
    }

    private void tryLogin() {
        System.out.println("tryLogin");
        browserInstance.getDriver().findElements(By.id("mat-input-0")).get(0).sendKeys("p432508.6911");
        browserInstance.getDriver().findElements(By.id("mat-input-1")).get(0).sendKeys("15470707");
        browserInstance.getDriver().findElements(By.tagName("button")).get(0).click();
    }

    private static boolean verifyElements(final List<WebElement> elements) {
        return elements != null && elements.size() > 0;
    }
}
