package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Option
import com.emanuelvictor.api.functional.flowcreator.infrastructure.aid.Utils
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.generic.PersistentEntity
import java.util.stream.Collectors

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
abstract class AbstractAlternative(val messageToNext: String, val nextIsMultipleChoice: Boolean, vararg val options: Option) : PersistentEntity() {

    constructor(messageToNext: String, nextIsMultipleChoice: Boolean = false, values: List<Option>) : this(messageToNext, nextIsMultipleChoice, *values.toTypedArray())

    internal companion object {
        const val SEPARATOR = "->"
    }

    /**
     * @return the path
     */
    abstract val path: String

    /**
     * @param {@link Set<String>} The set who will be converted to String.
     * @return {@link String} The set converted to String
     */
    open fun valuesToString(): String {
        if (options.size == 1) {
            return options.first().value
        }
        return options.toList().stream().map { it.value }.collect(Collectors.toList()).toString()
    }
}