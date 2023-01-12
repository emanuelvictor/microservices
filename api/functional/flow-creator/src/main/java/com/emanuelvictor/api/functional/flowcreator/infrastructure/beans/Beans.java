package com.emanuelvictor.api.functional.flowcreator.infrastructure.beans;

import com.emanuelvictor.api.functional.flowcreator.domain.ports.AlternativeRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.services.AlternativeService;
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
}
