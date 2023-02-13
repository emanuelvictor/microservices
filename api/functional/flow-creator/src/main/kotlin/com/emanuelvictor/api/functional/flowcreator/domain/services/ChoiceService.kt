package com.emanuelvictor.api.functional.flowcreator.domain.services

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.ChoiceRepository

/**
 *
 */
class ChoiceService(private val choiceRepository: ChoiceRepository) {
    /**
     * @param alternative [IntermediaryAlternative]
     * @return [Choice]
     */
    fun makeChoice(alternative: IntermediaryAlternative?): Choice {
        return choiceRepository.save(Choice(alternative!!))
    }
}