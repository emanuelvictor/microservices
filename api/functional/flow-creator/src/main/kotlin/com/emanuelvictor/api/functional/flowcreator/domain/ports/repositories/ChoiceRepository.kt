package com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice
import io.github.emanuelvictor.commons.persistence.CrudRepository

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
interface ChoiceRepository : CrudRepository<Choice, Int> {

    /**
     * todo MAKE tests
     */
    fun listChoicesByOptionsValues(vararg values: String): List<Choice>
}