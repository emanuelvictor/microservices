package com.emanuelvictor.api.functional.bertosimulators.infrastructure.browser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PANFlow {

    private BrowserInstance browserInstance;

    @BeforeEach
    public void setUp() {
//        final BrowserOptions browserOptions = BrowserOptions.create("c158dde2915f2eeb6bbf38b273020fad", "http://localhost:34883");
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
        browserInstance.getDriver().get("https://veiculos.bancopan.com.br/captura/inicio");
    }

    private void tryLogin() {
        System.out.println("tryLogin");
        browserInstance.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        final List<WebElement> username = browserInstance.getDriver().findElements(By.id("login"));
        username.get(0).sendKeys("10650570910");
        final List<WebElement> password = browserInstance.getDriver().findElements(By.id("password"));
        password.get(0).sendKeys("#Berto129");
        browserInstance.getDriver().findElements(By.className("pan-mahoe-button--primary")).get(0).click();
    }

    private void waitUntilDocumentFieldIsShowing() {
        System.out.println("waitUntilDocumentFieldIsShowing");
        final Wait<WebDriver> wait = new WebDriverWait(browserInstance.getDriver(), Duration.ofSeconds(60));
        wait.until(d -> {
            final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("cpf"));
            return verifyElements(elements);
        });
    }

    private void fillDocument(final String document) {
        System.out.println("fillDocument");
        final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("cpf"));
        if (verifyElements(elements)) elements.get(0).sendKeys(document);
    }

    private void choiceCarAsTypeOfVehicle() {
        System.out.println("choiceCarAsTypeOfVehicle");
        final List<WebElement> elements = browserInstance.getDriver().findElements(By.className("pan-mahoe-card"));
        if (verifyElements(elements)) elements.get(0).click();
    }

    private void continueToFillSimulationData() {
        System.out.println("continueToFillSimulationData");
        final List<WebElement> elements = browserInstance.getDriver().findElements(By.className("mahoe-button"));
        if (verifyElements(elements)) elements.get(0).click();
    }

    private void waitUntilCellNumberIsShowing() {
        System.out.println("waitUntilCellNumberIsShowing");
        final Wait<WebDriver> wait = new WebDriverWait(browserInstance.getDriver(), Duration.ofSeconds(60));
        wait.until(d -> {
            final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("cellnumber"));
            return verifyElements(elements);
        });
    }

    private void fillCellNumber(final String cellNumber) {
        System.out.println("fillCellNumber");
        final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("cellnumber"));
        if (verifyElements(elements)) elements.get(0).sendKeys(cellNumber);
    }

    private void fillPlateNumber(final String plateNumber) {
        System.out.println("fillPlateNumber");
        final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("plate"));
        if (elements != null && elements.size() > 0) elements.get(0).sendKeys(plateNumber);
    }

    private void waitUntilCotationFieldIsShowing() {
        System.out.println("waitUntilCotationFieldIsShowing");
        final Wait<WebDriver> wait = new WebDriverWait(browserInstance.getDriver(), Duration.ofSeconds(1000), Duration.ofSeconds(2));
        wait.until(d -> {
            final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("value"));
            return verifyElements(elements);
        });
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
        final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("requestedEntry"));
        if (verifyElements(elements)) elements.get(0).sendKeys(requestedEntry);
    }

    private void waitUntilConditionPropostButtonIsShowing() {
        System.out.println("waitUntilConditionPropostButtonIsShowing");
        final Wait<WebDriver> wait = new WebDriverWait(browserInstance.getDriver(), Duration.ofSeconds(1000), Duration.ofSeconds(2));
        wait.until(d -> {
            final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("btnNexta"));
            return verifyElements(elements) && (elements.get(0).getAttribute("disabled") == null
                    || Objects.equals(elements.get(0).getAttribute("disabled"), "false"));
        });
    }

    private void continueToConditionPropost() {
        System.out.println("continueToConditionPropost");
        final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("btnNexta"));
        if (verifyElements(elements)) elements.get(0).click();
    }

    private void waitUntilFinancialDataIsShowing() {
        System.out.println("waitUntilFinancialDataIsShowing");
        final Wait<WebDriver> wait = new WebDriverWait(browserInstance.getDriver(), Duration.ofSeconds(1000), Duration.ofSeconds(2));
        wait.until(d -> {
            final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("pan-mahoe-select-9"));
            return verifyElements(elements);
        });
    }

    private void selectPAN5() {
        System.out.println("selectPAN5");
        List<WebElement> elements = browserInstance.getDriver().findElements(By.id("pan-mahoe-select-9"));
        if (verifyElements(elements)) {
            elements.get(0).click();
            elements = browserInstance.getDriver().findElements(By.className("pan-mahoe-select-option--show-in-filter"));
            if (verifyElements(elements)) {
                elements.get(elements.size() - 1).click();
            }
        }
    }

    private void waitUntilToButtonRecalcIsShowing() {
        System.out.println("waitUntilToButtonRecalcIsShowing");
        final Wait<WebDriver> wait = new WebDriverWait(browserInstance.getDriver(), Duration.ofSeconds(1000), Duration.ofSeconds(2));
        wait.until(d -> {
            final List<WebElement> elements = browserInstance.getDriver().findElements(By.className("pan-mahoe-button--primary"));
            return verifyElements(elements);
        });
    }

    private void clickOnRecalcButton() {
        System.out.println("clickOnRecalcButton");
        final List<WebElement> elements = browserInstance.getDriver().findElements(By.className("pan-mahoe-button--primary"));
        if (verifyElements(elements)) elements.get(0).click();
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
        final String[] dataInString = browserInstance.getDriver().findElements(By.className("resume-v2")).get(0).getText().split("\n");
        final List<String> dataInList = new java.util.ArrayList<>(Arrays.stream(dataInString).toList());
        dataInList.remove(0); // Remove resume
        dataInList.remove(0); // Remove approved propost
        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0)
                System.out.print(dataInList.get(i) + " ");
            else
                System.out.println(dataInList.get(i));
        }
    }


    private static boolean verifyElements(final List<WebElement> elements) {
        return elements != null && elements.size() > 0;
    }
}
