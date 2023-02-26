package com.emanuelvictor.api.functional.flowcreator.domain.repositories

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.Alternative
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
interface AlternativeRepository : JpaRepository<Alternative, Long> {

    /**
     *
     */
    fun findByPath(path: String): Optional<Alternative>

}