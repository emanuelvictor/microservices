package com.emanuelvictor.api.functional.bertosimulator.infrastructure.adapters.browser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PANFlow {

    private BrowserInstance browserInstance;

    @BeforeEach
    public void setUp() {
        final BrowserOptions browserOptions = BrowserOptions.create(
                "ae46729368e328ee2f1684fc3ae6b036",
                "http://localhost:2173"
        );
        browserInstance = BrowserInstance.openCreated(browserOptions);
//        browserInstance = BrowserInstance.createNew("https://veiculos.bancopan.com.br/captura/inicio");
    }

    @Test
    void flowOfPANBank(){
        System.out.println(browserInstance.getUrl());
        System.out.println(browserInstance.getSessionId());

        fillDocument("05920838531");
        choiceCarAsTypeOfVehicle();
        continueToFillSimulationData();
//        browserInstance.getDriver().findElements(By.id("cpf")).get(0).sendKeys("05920838531");
//        browserInstance.getDriver().findElements(By.className("pan-mahoe-card")).get(0).click();
//        browserInstance.getDriver().findElements(By.className("mahoe-button")).get(0).click();

        // Esperar um tempão

        browserInstance.getDriver().findElements(By.className("cellnumber")).get(0).sendKeys("45978944646");
        browserInstance.getDriver().findElements(By.className("cellnumber")).get(0).sendKeys("JVO6879");

        System.out.println("asdfasdfa");

//        browserInstance.openOnNewTab("https://veiculos.bancopan.com.br/captura/inicio");
    }

    private void fillDocument(final String document){
        final List<WebElement> elements = browserInstance.getDriver().findElements(By.id("cpf"));
        if(elements != null && elements.size() > 1)
            elements.get(0).sendKeys(document);
    }

    private void choiceCarAsTypeOfVehicle(){
        final List<WebElement> elements = browserInstance.getDriver().findElements(By.className("pan-mahoe-card"));
        if(elements != null && elements.size() > 1)
            elements.get(0).click();
    }

    private void continueToFillSimulationData(){
        final List<WebElement> elements = browserInstance.getDriver().findElements(By.className("mahoe-button"));
        if(elements != null && elements.size() > 1)
            elements.get(0).click();
    }

//    @Test
//    void test() {
//
//        browserInstance.openOnNewTab("https://parceiro.bv.com.br/spa-ppar-base-dealer-veiculo/aprovometro/simulacao/dados-do-cliente/");
//    }
}
