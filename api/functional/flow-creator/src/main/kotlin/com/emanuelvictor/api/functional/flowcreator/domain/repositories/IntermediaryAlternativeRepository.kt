package com.emanuelvictor.api.functional.flowcreator.domain.repositories

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
interface IntermediaryAlternativeRepository : JpaRepository<IntermediaryAlternative, Long> {

    /**
     * TODO MAKE tests
     * TODO verify posibility to return a List
     */
    fun findAllByPreviousId(previousId: Long): Set<IntermediaryAlternative>

}