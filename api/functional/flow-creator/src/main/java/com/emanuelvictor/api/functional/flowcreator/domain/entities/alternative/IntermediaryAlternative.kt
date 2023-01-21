package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
class IntermediaryAlternative(val previous: AbstractAlternative, values: List<String>, messageToNext: String, nextIsMultipleChoice: Boolean = false) :
    AbstractAlternative(values, messageToNext, nextIsMultipleChoice) {

    constructor(previous: AbstractAlternative, value: String, messageToNext: String, nextIsMultipleChoice: Boolean = false) : this(previous, arrayListOf(value), messageToNext, nextIsMultipleChoice)

    /**
     * @return This recursive method return the total path from the alternative.
     * Example: 'Bubblemix Tea->Catuaí Palladium - Foz do Iguaçu->Vilma'.
     */
    override val path: String
        get() = previous.path + SEPARATOR + valuesToString()

    /**
     * @param alternative {@link IntermediaryAlternative} to compare values
     */
    fun compareValues(alternative: IntermediaryAlternative): Boolean {
        values.forEach {
            for ((externalIndex, externalValue) in alternative.values.withIndex()) {
                if (it == (externalValue))
                    break
                else if (externalIndex == alternative.values.size - 1)
                    return false
            }
        }
        return (alternative.values.size == values.size)
    }
}