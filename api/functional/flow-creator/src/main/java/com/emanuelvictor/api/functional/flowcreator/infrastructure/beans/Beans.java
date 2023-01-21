package com.emanuelvictor.api.functional.flowcreator.infrastructure.beans;

import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.AlternativeRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.ChoiceRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.services.AlternativeService;
import com.emanuelvictor.api.functional.flowcreator.domain.services.ChoiceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class Beans {

    /**
     * @param alternativeRepository {@link AlternativeRepository}
     * @return {@link AlternativeService}
     */
    @Bean
    public AlternativeService alternativeService(final AlternativeRepository alternativeRepository) {
        return new AlternativeService(alternativeRepository);
    }

    /**
     * @param choiceRepository {@link ChoiceRepository}
     * @return {@link ChoiceService}
     */
    @Bean
    public ChoiceService choiceService(final ChoiceRepository choiceRepository) {
        return new ChoiceService(choiceRepository);
    }
}
