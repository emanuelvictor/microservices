package com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Node
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.CrudRepository
import java.util.*

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
interface NodeRepository : CrudRepository<Node, Long> {

    /**
     *
     */
    fun findByValue(value: String): Optional<Node>
}