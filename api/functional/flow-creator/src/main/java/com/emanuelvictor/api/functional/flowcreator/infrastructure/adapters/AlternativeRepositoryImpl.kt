package com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters

import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.AbstractAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.ports.AlternativeRepository
import com.emanuelvictor.api.functional.flowcreator.domain.ports.IntermediaryAlternativeRepository
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.AbstractRepository
import org.springframework.stereotype.Repository
import java.util.stream.Stream
import kotlin.streams.toList

/**
 *
 */
@Repository
class AlternativeRepositoryImpl : AbstractRepository<AbstractAlternative?, Long?>(), AlternativeRepository {

    override fun findByPreviousId(previousId: Long): Stream<IntermediaryAlternative> {
        return stream
            .filter { persistentEntity ->
                (persistentEntity is IntermediaryAlternative) && (persistentEntity.previous.id == previousId)
            } as Stream<IntermediaryAlternative>
    }

    override fun findAllRootAlternatives(): Stream<RootAlternative> {
        return stream.filter { persistentEntity ->
            (persistentEntity is RootAlternative)
        } as Stream<RootAlternative>
    }

    override fun findAllIntermediaryAlternatives(): Stream<IntermediaryAlternative> {
        return stream.filter { persistentEntity ->
            (persistentEntity is IntermediaryAlternative)
        } as Stream<IntermediaryAlternative>
    }

    companion object {
        fun areTheClassesEquals(clazz: Class<*>, clazz2: Class<*>): Boolean {
            return clazz == clazz2
        }
    }
}