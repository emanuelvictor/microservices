package com.emanuelvictor.api.functional.flowcreator.domain.ports

import com.emanuelvictor.api.functional.flowcreator.domain.entity.Choice
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.CrudRepository

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
interface ChoiceRepository : CrudRepository<Choice?, Long?>