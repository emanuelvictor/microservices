package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.OptionPersist
import com.emanuelvictor.api.functional.flowcreator.domain.entities.question.Question
import com.emanuelvictor.api.functional.flowcreator.domain.entities.question.QuestionPersist
import jakarta.persistence.*

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
@Entity
class IntermediaryAlternativePersist : AlternativePersist {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alternative_id", nullable = false)
    var previous: AlternativePersist? = null

    constructor(previous: AlternativePersist, question: QuestionPersist, vararg options: OptionPersist) : super(question, false, *options) {
        this.previous = previous
    }

}