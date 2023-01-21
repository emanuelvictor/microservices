package com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Edge
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.EdgeRepository
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.AbstractRepository
import org.springframework.stereotype.Repository

/**
 *
 */
@Repository
class EdgeRepositoryImpl : AbstractRepository<Edge, Long>(), EdgeRepository