package com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories

import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option
import io.github.emanuelvictor.commons.persistence.CrudRepository

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
interface OptionRepository : CrudRepository<Option, Int> {

    /**
     *
     */
    fun listByValue(value: String): List<Option>
}