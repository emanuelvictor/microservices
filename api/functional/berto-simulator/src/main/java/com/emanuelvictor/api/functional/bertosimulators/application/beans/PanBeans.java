package com.emanuelvictor.api.functional.bertosimulators.application.beans;

import com.emanuelvictor.api.functional.bertosimulators.domain.ports.simulators.PanSimulator;
import com.emanuelvictor.api.functional.bertosimulators.domain.ports.simulators.Simulator;
import com.emanuelvictor.api.functional.bertosimulators.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.bertosimulators.infrastructure.browser.BrowserInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
