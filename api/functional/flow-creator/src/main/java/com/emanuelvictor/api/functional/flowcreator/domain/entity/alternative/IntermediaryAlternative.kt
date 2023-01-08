package com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entity.Option

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
class IntermediaryAlternative(val previous: AbstractAlternative, option: Option, messageToNext: String) :
    AbstractAlternative(option, messageToNext) {

//    val previous: AbstractAlternative
//
//    init {
//        this.previous = previous
//    }

    /**
     * @return This recursive method return the total path from the alternative.
     * Example: 'Bubblemix Tea->Catuaí Palladium - Foz do Iguaçu->Vilma'.
     */
    override val path: String
        get() = previous.path + SEPARATOR + option.name

    internal companion object {
        const val SEPARATOR = "->"
    }
}