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
public class FillDocument extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;

    private final PanStep choiceCarAsTypeOfVehicle;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("fillDocument");
        final String document = simulation.getDocumentFromCustomer();
        browserInstance.getElementById("cpf").sendKeys(document);
        choiceCarAsTypeOfVehicle.execute(simulation);
    }

}
