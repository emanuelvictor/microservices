package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
class IntermediaryAlternative(val previous: Alternative, messageToNext: String, nextIsMultipleChoice: Boolean = false, vararg options: Option) :
    Alternative(messageToNext, nextIsMultipleChoice, *options) {

    constructor(previous: Alternative, messageToNext: String, vararg options: Option) : this(previous, messageToNext, false, *options)

    constructor(previous: Alternative, messageToNext: String, nextIsMultipleChoice: Boolean = false, values: List<Option>) : this(
        previous,
        messageToNext,
        nextIsMultipleChoice,
        *values.toTypedArray()
    )

    /**
     * @return This recursive method return the total path from the alternative.
     * Example: 'Bubblemix Tea->Catuaí Palladium - Foz do Iguaçu->Vilma'.
     */
    override val path: String
        get() = previous.path + SEPARATOR + optionsValuesToString()

    override val signature: String
        get() = previous.signature + SEPARATOR + optionsIdsToString()

    /**
     * @param alternative {@link IntermediaryAlternative} to compare values
     */
    fun compareValues(alternative: IntermediaryAlternative): Boolean {
        options.forEach {
            for ((externalIndex, externalOption) in alternative.options.withIndex()) {
                if (it.identifier == externalOption.identifier)
                    break
                else if (externalIndex == alternative.options.size - 1)
                    return false
            }
        }
        return (alternative.options.size == options.size)
    }
}