package com.emanuelvictor.api.functional.bertosimulators.infrastructure.browser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.emanuelvictor.api.functional.bertosimulators.infrastructure.browser.BrowserInstance.verifyElements;

public class PANFlowOld {

    private BrowserInstance browserInstance;

    @BeforeEach
    public void setUp() {
//        final BrowserOptions browserOptions = BrowserOptions.create("19c6706aa65645a7a07250da4a437562", "http://localhost:39188");
//        browserInstance = BrowserInstance.openCreated(browserOptions);
        browserInstance = BrowserInstance.createNew("https://veiculos.bancopan.com.br/captura/inicio");
    }

    @Test
    void flowOfPANBank() {

        goToBegin();
        tryLogin();
        waitUntilDocumentFieldIsShowing();

        fillDocument("05920838531");
        choiceCarAsTypeOfVehicle();
        continueToFillSimulationData();

        waitUntilCellNumberIsShowing();

        fillCellNumber("45978944646");
        fillPlateNumber("JVO6879");

        waitUntilCotationFieldIsShowing();

        fillCotation("20000");
        fillRequestedEntry("5000");

        waitUntilConditionPropostButtonIsShowing();
        continueToConditionPropost();
        waitUntilFinancialDataIsShowing();
        selectPAN5();

        waitUntilToButtonRecalcIsShowing();
        clickOnRecalcButton();

        waitUntilToButtonProceedIsShowing();

        extractData();
    }

    private void goToBegin() {
        System.out.println("goToBegin");
        browserInstance.goTo("https://veiculos.bancopan.com.br/captura/inicio");
    }

    private void tryLogin() {
        System.out.println("tryLogin");
        browserInstance.waitFor(4);
        final WebElement usernameInput = browserInstance.getElementById("login");
        usernameInput.sendKeys("10650570910");
        final WebElement passwordInput = browserInstance.getElementById("password");
        passwordInput.sendKeys("#Berto129");
        browserInstance.getElementsByClassName("pan-mahoe-button--primary").get(0).click();
    }

    private void waitUntilDocumentFieldIsShowing() {
        System.out.println("waitUntilDocumentFieldIsShowing");
        browserInstance.waitForElementsIds("cpf");
    }

    private void fillDocument(final String document) {
        System.out.println("fillDocument");
        browserInstance.getElementById("cpf").sendKeys(document);
    }

    private void choiceCarAsTypeOfVehicle() {
        System.out.println("choiceCarAsTypeOfVehicle");
        final List<WebElement> elements = browserInstance.getElementsByClassName("pan-mahoe-card");
        if (verifyElements(elements)) elements.get(0).click();
    }

    private void continueToFillSimulationData() {
        System.out.println("continueToFillSimulationData");
        final List<WebElement> elements = browserInstance.getElementsByClassName("mahoe-button");
        if (verifyElements(elements)) elements.get(0).click();
    }

    private void waitUntilCellNumberIsShowing() {
        System.out.println("waitUntilCellNumberIsShowing");
        browserInstance.waitForElementsIds("cellnumber");
    }

    private void fillCellNumber(final String cellNumber) {
        System.out.println("fillCellNumber");
        browserInstance.getElementById("cellnumber").sendKeys(cellNumber);
    }

    private void fillPlateNumber(final String plateNumber) {
        System.out.println("fillPlateNumber");
        browserInstance.getElementById("plate").sendKeys(plateNumber);
    }

    private void waitUntilCotationFieldIsShowing() {
        System.out.println("waitUntilCotationFieldIsShowing");
        browserInstance.waitForElementsIds(Duration.ofSeconds(1000), Duration.ofSeconds(2), "value");
    }

    private void fillCotation(String cotation) {
        System.out.println("fillCotation");
        if (cotation == null) {
            cotation = browserInstance.getDriver().findElements(By.className("state-values__assisted")).get(0).getText();
            cotation = cotation.replace("Cotação: R$", "").replace(",00", "") + ".00";
        }
        final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("value"));
        if (verifyElements(elements)) elements.get(0).sendKeys(cotation);
    }

    private void fillRequestedEntry(String requestedEntry) {
        System.out.println("fillRequestedEntry");
        browserInstance.getElementById("requestedEntry").sendKeys(requestedEntry);
    }

    private void waitUntilConditionPropostButtonIsShowing() {
        System.out.println("waitUntilConditionPropostButtonIsShowing");
        final Wait<WebDriver> wait = new WebDriverWait(browserInstance.getDriver(), Duration.ofSeconds(1000), Duration.ofSeconds(2));
        wait.until(d -> {
            final WebElement element = browserInstance.getElementById("btnNexta");
            return verifyElements(element) && (element.getAttribute("disabled") == null
                    || Objects.equals(element.getAttribute("disabled"), "false"));
        });
    }

    private void continueToConditionPropost() {
        System.out.println("continueToConditionPropost");
        browserInstance.getElementById("btnNexta").click();
    }

    private void waitUntilFinancialDataIsShowing() {
        System.out.println("waitUntilFinancialDataIsShowing");
//        browserInstance.waitForElementsIds(Duration.ofSeconds(1000), Duration.ofSeconds(2), "pan-mahoe-select-9");
        final Wait<WebDriver> wait = new WebDriverWait(browserInstance.getDriver(), Duration.ofSeconds(1000), Duration.ofSeconds(2));
        wait.until(d -> {
            final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("pan-mahoe-select-9")); // TODO B.O aqui
            return verifyElements(elements);
        });
    }

    private void selectPAN5() {
        System.out.println("selectPAN5");
        final WebElement element = browserInstance.getElementById("pan-mahoe-select-9");
        if (verifyElements(element)) {
            element.click();
            final List<WebElement> options = browserInstance
                    .getElementsByClassName("pan-mahoe-select-option--show-in-filter");
            if (verifyElements(options))
                options.get(options.size() - 1).click();
        }
    }

    private void waitUntilToButtonRecalcIsShowing() {
        System.out.println("waitUntilToButtonRecalcIsShowing");
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
    }

    private void clickOnRecalcButton() {
        System.out.println("clickOnRecalcButton");
        final List<WebElement> elements = browserInstance.getElementsByTagName("button");
        elements.forEach(webElement -> {
            if (webElement.getAttribute("label") != null &&
                    webElement.getAttribute("label").equals("Recalcular")) {
                webElement.click();
            }
        });
    }

    private void waitUntilToButtonProceedIsShowing() {
        System.out.println("waitUntilToButtonProceedIsShowing");
        final Wait<WebDriver> wait = new WebDriverWait(browserInstance.getDriver(), Duration.ofSeconds(1000), Duration.ofSeconds(2));
        wait.until(d -> {
            final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("btnToProposal"));
            return verifyElements(elements) && (elements.get(0).getAttribute("disabled") == null
                    || Objects.equals(elements.get(0).getAttribute("disabled"), "false"));
        });
    }

    private void extractData() {
        System.out.println("extractData");
        final String[] dataInString =
                browserInstance
                        .getElementsByClassName("resume-v2")
                        .get(0)
                        .getText()
                        .split("\n");
        final List<String> dataInList = new ArrayList<>(Arrays.stream(dataInString).toList());
        dataInList.remove(0); // Remove resume
        dataInList.remove(0); // Remove approved propost
        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0)
                System.out.print(dataInList.get(i) + " ");
            else
                System.out.println(dataInList.get(i));
        }
    }
}
