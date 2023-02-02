package com.emanuelvictor.api.functional.flowcreator.domain.entities

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.Alternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.generic.PersistentEntity
import java.time.LocalDateTime

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
class Choice(val alternative: IntermediaryAlternative) : PersistentEntity() {

    val date = LocalDateTime.now()

    val options: HashSet<Option> = HashSet()
        get() {
            getOptionFromAlternative(alternative, field)
            return field
        }

    /**
     *
     */
    val signature: String
        get() = alternative.signature

    /**
     *
     */
    val path: String
        get() = alternative.path

    /**
     *
     */
    private fun getOptionFromAlternative(alternative: Alternative, options: HashSet<Option>) {
        if (alternative is IntermediaryAlternative)
            getOptionFromAlternative(alternative.previous, options)
        options.addAll(alternative.options)
    }

}
