package com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Edge
import com.emanuelvictor.api.functional.flowcreator.domain.entities.Node
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.EdgeRepository
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.NodeRepository
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.AbstractRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 *
 */
@Repository
class NodeRepositoryImpl : AbstractRepository<Node, Long>(), NodeRepository {
    override fun findByValue(value: String): Optional<Node> {
        return this.stream.filter { it.value == value }.findFirst()
    }
}