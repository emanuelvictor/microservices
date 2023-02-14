package com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.Alternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.AlternativeRepository
import io.github.emanuelvictor.commons.persistence.generic.AbstractRepository
import org.springframework.stereotype.Repository
import java.util.stream.Stream

/**
 *
 */
@Repository
class AlternativeRepositoryImpl : AbstractRepository<Alternative, Int>(), AlternativeRepository {

    override fun findChildrenFromAlternativeId(id: Int): Stream<IntermediaryAlternative> {
        return stream
            .filter { persistentEntity ->
                (persistentEntity is IntermediaryAlternative) && (persistentEntity.previous.id == id)
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

}