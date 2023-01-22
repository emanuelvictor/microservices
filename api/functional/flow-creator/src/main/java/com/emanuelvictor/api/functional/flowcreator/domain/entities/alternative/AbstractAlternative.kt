package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.infrastructure.aid.Utils
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.generic.PersistentEntity

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
abstract class AbstractAlternative(val messageToNext: String, val nextIsMultipleChoice: Boolean, vararg val values: String) : PersistentEntity() {

    constructor(messageToNext: String, nextIsMultipleChoice: Boolean = false, values: List<String>) : this(messageToNext, nextIsMultipleChoice, *values.toTypedArray())

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
        if (values.size == 1) {
            return values.first()
        }
        return Utils.getListFromArray(values).toString()
    }
}