package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.application.beans;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.services.PanSimulator;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.services.Simulator;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.browser.BrowserInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PanBeans {

    @Bean
    BrowserInstance panBrowserInstance() {
        return BrowserInstance.createNew("https://veiculos.bancopan.com.br/captura/inicio");
    }

    @Bean
    Simulator panSimulator(PanStep begin) {
        return new PanSimulator(begin);
    }
}
