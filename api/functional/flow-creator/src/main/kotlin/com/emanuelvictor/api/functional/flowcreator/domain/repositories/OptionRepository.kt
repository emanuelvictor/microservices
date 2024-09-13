package com.emanuelvictor.api.functional.flowcreator.domain.repositories

import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
interface OptionRepository : JpaRepository<Option, Long> {

    /**
     *
     */
    @Query("select o from Option o where FILTER(:value, o.identifier) = TRUE")
    fun listByValue(value: String): List<Option>

    /**
     *
     */
    fun findByIdentifier(valueFromUnit: String): Optional<Option>
}