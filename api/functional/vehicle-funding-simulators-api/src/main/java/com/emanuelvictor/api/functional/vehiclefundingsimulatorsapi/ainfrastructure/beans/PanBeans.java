package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.beans;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.services.PanSimulator;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.services.Simulator;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.browser.BrowserInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PanBeans {

    @Bean
    BrowserInstance panBrowserInstance() {
        return BrowserInstance.createEmpty();
    }

    @Bean
    Simulator panSimulator(PanStep begin) {
        return new PanSimulator(begin);
    }
}
