package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.steps.pan;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.Simulation;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.steps.AbstractStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.browser.BrowserInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
