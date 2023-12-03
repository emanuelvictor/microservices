package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.steps;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.VehicleFinancingSimulatorsApiApplication;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStep {

    public final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    protected boolean verifyElements(final List<WebElement> elements) {
        return elements != null && elements.size() > 0;
    }

    protected boolean verifyElements(final WebElement... elements) {
        return elements != null && elements.length > 0;
    }
}
