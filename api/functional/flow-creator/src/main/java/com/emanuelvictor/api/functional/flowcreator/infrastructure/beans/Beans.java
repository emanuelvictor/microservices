package com.emanuelvictor.api.functional.flowcreator.infrastructure.beans;

import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.AlternativeRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.ChoiceRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.OptionRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.services.AlternativeService;
import com.emanuelvictor.api.functional.flowcreator.domain.services.ChoiceService;
import com.emanuelvictor.api.functional.flowcreator.infrastructure.helpers.PopulateHelper;
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

    /**
     * @param choiceService      {@link ChoiceService}
     * @param optionRepository   {@link OptionRepository}
     * @param choiceRepository   {@link ChoiceRepository}
     * @param alternativeService {@link AlternativeService}
     * @return {@link PopulateHelper}
     */
    @Bean
    public PopulateHelper populateHelper(final ChoiceService choiceService, final OptionRepository optionRepository, final ChoiceRepository choiceRepository, final AlternativeService alternativeService) {
        return new PopulateHelper(choiceService, optionRepository, choiceRepository, alternativeService);
    }
}
