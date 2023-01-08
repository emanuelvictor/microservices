package com.emanuelvictor.api.functional.flowcreator.domain.entity

import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.AbstractAlternative
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.generic.PersistentEntity

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
class Choice(private val alternative: AbstractAlternative) : PersistentEntity() {

    /**
     *
     */
    fun getPath(): String {
        return alternative.path
    }

}
