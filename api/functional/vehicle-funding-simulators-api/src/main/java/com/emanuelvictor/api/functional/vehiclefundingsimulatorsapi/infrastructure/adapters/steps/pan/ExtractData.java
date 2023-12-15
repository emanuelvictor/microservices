package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.steps.pan;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.Simulation;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.steps.pan.PanStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.steps.AbstractStep;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.browser.BrowserInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExtractData extends AbstractStep implements PanStep {

    private final BrowserInstance browserInstance;

    @Override
    public void execute(final Simulation simulation) {
        logger.info("extractData");
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
