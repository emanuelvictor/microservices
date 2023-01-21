package com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.ChoiceRepository
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.AbstractRepository
import org.springframework.stereotype.Repository

/**
 *
 */
@Repository
class ChoiceRepositoryImpl : AbstractRepository<Choice?, Long?>(), ChoiceRepository