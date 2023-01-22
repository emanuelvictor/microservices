package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
class RootAlternative(messageToNext: String, nextIsMultipleChoice: Boolean = false, value: String) : AbstractAlternative(messageToNext, nextIsMultipleChoice, value) {

    constructor(messageToNext: String, value: String) : this(messageToNext, false, value)

    /**
     * @return the name of the option.
     */
    override val path: String
        get() = valuesToString()
}