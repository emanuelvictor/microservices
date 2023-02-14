package com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.AbstractAlternative
import io.github.emanuelvictor.commons.persistence.CrudRepository

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
interface AlternativeRepository : RootAlternativeRepository, IntermediaryAlternativeRepository, CrudRepository<AbstractAlternative, Int>