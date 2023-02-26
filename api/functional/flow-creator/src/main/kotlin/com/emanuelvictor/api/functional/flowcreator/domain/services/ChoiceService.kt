package com.emanuelvictor.api.functional.flowcreator.domain.services

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.repositories.ChoiceRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

/**
 *
 */
class ChoiceService(private val choiceRepository: ChoiceRepository) {

    /**
     * @param alternative [IntermediaryAlternative]
     * @return [Choice]
     */
    fun makeChoice(alternative: IntermediaryAlternative): Choice {
        return choiceRepository.save(Choice(alternative))
    }

    /**
     *
     */
    fun listByFilters(defaultFilter: String?, pageable: Pageable): Page<Choice> {
        return PageImpl(choiceRepository.findAll().toList())
    }
}