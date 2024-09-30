package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.OptionPersist
import com.emanuelvictor.api.functional.flowcreator.domain.entities.question.QuestionPersist
import jakarta.persistence.Entity

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
@Entity
class RootAlternativePersist : AlternativePersist {
    constructor(question: QuestionPersist, nextIsMultipleChoice: Boolean = false, option: OptionPersist) : super(
        question,
        nextIsMultipleChoice,
        option
    )

    constructor(question: QuestionPersist, option: OptionPersist) : this(question, false, option)
}