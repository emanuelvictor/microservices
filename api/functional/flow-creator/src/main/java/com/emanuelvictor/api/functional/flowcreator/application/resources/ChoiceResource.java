package com.emanuelvictor.api.functional.flowcreator.application.resources;

import com.emanuelvictor.api.functional.flowcreator.application.resources.generic.AbstractResource;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.Choice;
import com.emanuelvictor.api.functional.flowcreator.domain.services.ChoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("choices")
public class ChoiceResource extends AbstractResource<Choice, Long> {

    private final ChoiceService choiceService;

    /**
     * @param defaultFilter String
     * @return Page<AbstractAlternative>
     */
    @GetMapping
    public Page<Choice> listByFilters(final String defaultFilter, final Pageable pageable) {
        return choiceService.listByFilters(defaultFilter, pageable);
    }

    /**
     * @param intermediaryAlternative
     * @return
     */
    @GetMapping("make")
    public Choice makeChoice(@RequestBody final IntermediaryAlternative intermediaryAlternative) {
        return choiceService.makeChoice(intermediaryAlternative);
    }
}
