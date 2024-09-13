package com.emanuelvictor.api.functional.flowcreator.domain.repositories

import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.ChoiceId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
interface ChoiceRepository : JpaRepository<Choice, ChoiceId> {

    /**
     * TODO remove vararg
     */
    @Query("select c from Choice c where FILTER(:pathFilter, c.path)")
    fun listChoicesByOptionsValues(pathFilter: String): List<Choice>
}