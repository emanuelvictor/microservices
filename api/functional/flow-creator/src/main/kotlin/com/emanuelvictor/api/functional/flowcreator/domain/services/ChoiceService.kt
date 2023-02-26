package com.emanuelvictor.api.functional.flowcreator.domain.services

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.services.generic.AbstractService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

/**
 *
 */
class ChoiceService : AbstractService<Choice, Long>() {

    /**
     * @param alternative [IntermediaryAlternative]
     * @return [Choice]
     */
    fun makeChoice(alternative: IntermediaryAlternative): Choice {
        return repository.save(Choice(alternative))
    }

    /**
     *
     */
    fun listByFilters(defaultFilter: String?, pageable: Pageable): Page<Choice> {
        return PageImpl(repository.findAll().toList())
    }
}