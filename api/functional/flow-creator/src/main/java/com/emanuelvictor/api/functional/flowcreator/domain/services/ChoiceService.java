package com.emanuelvictor.api.functional.flowcreator.domain.services;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.Node;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.ChoiceRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.EdgeRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.NodeRepository;
import lombok.RequiredArgsConstructor;

/**
 *
 */
@RequiredArgsConstructor
public class ChoiceService {

    private final NodeRepository nodeRepository;
    private final EdgeRepository edgeRepository;
    private final ChoiceRepository choiceRepository;

    /**
     * @param alternative {@link IntermediaryAlternative}
     * @return {@link IntermediaryAlternative}
     */
    public Choice makeChoice(final IntermediaryAlternative alternative) {

        var choice = choiceRepository.save(new Choice(alternative));

        choice.getEdges().forEach(edge -> {
            nodeRepository.findByValue(edge.getSource().getValue()).ifPresent(edge::setSource);
            nodeRepository.findByValue(edge.getTarget().getValue()).ifPresent(edge::setTarget);

            if (edge.getSource().getId() == null)
                edge.setSource(nodeRepository.save(edge.getSource()));
            if (edge.getTarget().getId() == null)
                edge.setTarget(nodeRepository.save(edge.getTarget()));
            edgeRepository.save(edge);
        });

        return choice;
    }


}