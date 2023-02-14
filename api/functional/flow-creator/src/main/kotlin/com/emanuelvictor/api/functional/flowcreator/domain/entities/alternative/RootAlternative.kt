package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
class RootAlternative(messageToNext: String, nextIsMultipleChoice: Boolean = false, option: Option) : AbstractAlternative(messageToNext, nextIsMultipleChoice, option) {

    constructor(messageToNext: String, option: Option) : this(messageToNext, false, option)

    /**
     * @return the path from RootAlternative
     */
    override val path: String
        get() = optionsValuesToString()

    /**
     * @return the signature from RootAlternative
     */
    override val signature: String
        get() = optionsIdsToString()
}