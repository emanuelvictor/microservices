package com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters

import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.OptionRepository
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.AbstractRepository
import org.springframework.stereotype.Repository
import java.util.stream.Collectors

/**
 *
 */
@Repository
class OptionRepositoryImpl : AbstractRepository<Option, Int>(), OptionRepository {

    /**
     * TODO make tests
     */
    override fun listByValue(value: String): List<Option> {
        return this.stream.filter { it.identifier == value }.collect(Collectors.toList())
    }
}