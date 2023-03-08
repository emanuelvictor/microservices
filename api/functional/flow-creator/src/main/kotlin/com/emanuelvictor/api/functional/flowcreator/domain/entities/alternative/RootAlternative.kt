package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option
import com.emanuelvictor.api.functional.flowcreator.domain.entities.question.Question
import jakarta.persistence.Entity

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
@Entity
class RootAlternative : Alternative {

    constructor(question: Question, nextIsMultipleChoice: Boolean = false, option: Option) : super(question, nextIsMultipleChoice, listOf(option)) {
        this.generatePath()
        this.generateSignature()
    }

    constructor(question: Question, option: Option) : this(question, false, option)

}