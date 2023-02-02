package com.emanuelvictor.api.functional.flowcreator.domain.services;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.ChoiceRepository;
import lombok.RequiredArgsConstructor;

/**
 *
 */
@RequiredArgsConstructor
public class ChoiceService {

    private final ChoiceRepository choiceRepository;

    /**
     * @param alternative {@link IntermediaryAlternative}
     * @return {@link Choice}
     */
    public Choice makeChoice(final IntermediaryAlternative alternative) {

        return choiceRepository.save(new Choice(alternative));
    }


}