package com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entity.Option
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.generic.PersistentEntity

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
abstract class AbstractAlternative (val option: Option, val messageToNext: String): PersistentEntity() {

    /**
     * @return the name of the option.
     */
    open val path: String
        get() = option.name
}