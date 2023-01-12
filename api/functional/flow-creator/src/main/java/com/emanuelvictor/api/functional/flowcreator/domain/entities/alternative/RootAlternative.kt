package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
class RootAlternative(values: Set<String>, messageToNext: String, nextIsMultipleChoice: Boolean = false) : AbstractAlternative(values, messageToNext, nextIsMultipleChoice) {

    constructor(value: String, messageToNext: String, nextIsMultipleChoice: Boolean = false) : this(hashSetOf( value), messageToNext, nextIsMultipleChoice)

    /**
     * @return the name of the option.
     */
    override val path: String
        get() = valuesToString()
}