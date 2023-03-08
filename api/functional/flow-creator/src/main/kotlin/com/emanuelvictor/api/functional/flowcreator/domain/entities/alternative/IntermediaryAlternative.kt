package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option
import com.emanuelvictor.api.functional.flowcreator.domain.entities.question.Question
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
@Entity
class IntermediaryAlternative : Alternative {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alternative_id", nullable = false)
    var previous: Alternative? = null

    constructor(
        previous: Alternative, question: Question, nextIsMultipleChoice: Boolean = false, vararg options: Option
    ) : super(question, nextIsMultipleChoice, options.toList()) {
        this.previous = previous
        this.generatePath()
        this.generateSignature()
    }

    constructor(previous: Alternative, question: Question, nextIsMultipleChoice: Boolean = false, values: List<Option>) : this(
        previous, question, nextIsMultipleChoice, *values.toTypedArray()
    )

    constructor(previous: Alternative, question: Question, vararg options: Option) : this(previous, question, false, options.toList())

    /**
     * @return This recursive method return the total path from the alternative.
     * Example: 'Bubblemix Tea->Catuaí Palladium - Foz do Iguaçu->Vilma'.
     */
    override fun generatePath(): String {
        this.path = previous!!.generatePath() + SEPARATOR + optionsValuesToString()
        return this.path!!
    }

    /**
     *
     */
    override fun generateSignature() {
        this.signature = previous!!.signature + SEPARATOR + optionsIdsToString()  // TODO ta errado
    }

    /**
     * @param alternative {@link IntermediaryAlternative} to compare values
     */
    fun compareValues(alternative: IntermediaryAlternative): Boolean {
        options.forEach {
            for ((externalIndex, externalOption) in alternative.options.withIndex()) {
                if (it!!.identifier == externalOption!!.identifier) break
                else if (externalIndex == alternative.options.size - 1) return false
            }
        }
        return (alternative.options.size == options.size)
    }
}