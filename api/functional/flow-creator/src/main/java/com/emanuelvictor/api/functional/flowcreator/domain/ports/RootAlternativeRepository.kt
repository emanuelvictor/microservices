package com.emanuelvictor.api.functional.flowcreator.domain.ports

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import java.util.stream.Stream

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
interface RootAlternativeRepository {

    fun findAllRootAlternatives(): Stream<RootAlternative>
}