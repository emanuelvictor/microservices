package com.emanuelvictor.api.functional.flowcreator.domain.repositories.ports

import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.ChoiceId
import com.emanuelvictor.api.functional.flowcreator.domain.repositories.Repository

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
interface ChoiceRepository: Repository<Choice, ChoiceId> {

    fun listChoicesByOptionsValues(pathFilter: String): List<Choice>
}