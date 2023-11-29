package com.emanuelvictor.api.functional.bertosimulators.infrastructure.adapters.steps;

import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStep {

    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    protected boolean verifyElements(final List<WebElement> elements) {
        return elements != null && elements.size() > 0;
    }
}
