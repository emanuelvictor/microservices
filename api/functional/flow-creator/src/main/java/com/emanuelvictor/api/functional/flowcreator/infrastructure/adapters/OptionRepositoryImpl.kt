package com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters

import com.emanuelvictor.api.functional.flowcreator.domain.entity.Option
import com.emanuelvictor.api.functional.flowcreator.domain.ports.OptionRepository
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.AbstractRepository
import org.springframework.stereotype.Repository

/**
 *
 */
@Repository
class OptionRepositoryImpl : AbstractRepository<Option?, Long?>(), OptionRepository