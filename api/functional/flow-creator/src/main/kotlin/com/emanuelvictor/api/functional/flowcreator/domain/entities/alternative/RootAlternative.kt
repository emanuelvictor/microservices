package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option
import jakarta.persistence.Entity

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
@Entity
class RootAlternative : AbstractAlternative {

    constructor(messageToNext: String, nextIsMultipleChoice: Boolean = false, option: Option) : super(messageToNext, nextIsMultipleChoice, listOf(option)) {
        this.generatePath()
        this.generateSignature()
    }

    constructor(messageToNext: String, option: Option) : this(messageToNext, false, option)

}