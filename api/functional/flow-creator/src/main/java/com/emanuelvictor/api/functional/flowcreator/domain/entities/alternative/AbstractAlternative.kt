package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.generic.PersistentEntity

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
abstract class AbstractAlternative(val values: List<String>, val messageToNext: String, val nextIsMultipleChoice: Boolean) : PersistentEntity() {

    constructor(value: String, messageToNext: String, nextIsMultipleChoice: Boolean = false) : this(arrayListOf( value), messageToNext, nextIsMultipleChoice)
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
        return values.toString()
    }
}