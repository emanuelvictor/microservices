package com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import java.util.stream.Stream

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
interface IntermediaryAlternativeRepository {

    /**
     *
     */
    fun findChildrenFromAlternativeId(id: Int): Stream<IntermediaryAlternative>

    /**
     *
     */
    fun findAllIntermediaryAlternatives(): Stream<IntermediaryAlternative>
}