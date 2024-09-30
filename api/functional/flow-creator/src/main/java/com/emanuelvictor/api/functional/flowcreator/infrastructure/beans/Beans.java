package com.emanuelvictor.api.functional.flowcreator.infrastructure.beans;

import com.emanuelvictor.api.functional.flowcreator.domain.repositories.*;
import com.emanuelvictor.api.functional.flowcreator.domain.repositories.ports.ChoiceRepository;
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
     * @param rootAlternativeRepository         {@link RootAlternativeRepository}
     * @param alternativeRepository             {@link AlternativeRepository}
     * @param intermediaryAlternativeRepository {@link IntermediaryAlternativeRepository}
     * @return {@link AlternativeService}
     */
    @Bean
    public AlternativeService alternativeService(final RootAlternativeRepository rootAlternativeRepository, final AlternativeRepository alternativeRepository, final IntermediaryAlternativeRepository intermediaryAlternativeRepository) {
        return new AlternativeService(rootAlternativeRepository, alternativeRepository, intermediaryAlternativeRepository);
    }

    /**
     * @return {@link ChoiceService}
     */
    @Bean
    public ChoiceService choiceService() {
        return new ChoiceService();
    }

    /**
     * @param choiceService      {@link ChoiceService}
     * @param optionRepository   {@link OptionRepository}
     * @param choiceRepository   {@link ChoiceRepository}
     * @param questionRepository {@link QuestionRepository}
     * @param alternativeService {@link AlternativeService}
     * @return {@link PopulateHelper}
     */
    @Bean
    public PopulateHelper populateHelper(final ChoiceService choiceService, final OptionRepository optionRepository, final ChoiceRepository choiceRepository, final QuestionRepository questionRepository, final AlternativeService alternativeService) {
        return new PopulateHelper(choiceService, optionRepository, choiceRepository, questionRepository, alternativeService);
    }
}
