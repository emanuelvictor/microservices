package com.emanuelvictor.api.functional.flowcreator.domain.entities

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.generic.PersistentEntity

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
class Choice(val alternative: IntermediaryAlternative) : PersistentEntity() {

    /**
     *
     */
    fun getPath(): String {
        return alternative.path
    }

}
