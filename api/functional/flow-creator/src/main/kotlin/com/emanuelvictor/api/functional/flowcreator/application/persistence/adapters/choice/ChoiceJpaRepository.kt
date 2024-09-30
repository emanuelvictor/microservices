package com.emanuelvictor.api.functional.flowcreator.application.persistence.adapters.choice

import com.emanuelvictor.api.functional.flowcreator.application.persistence.AbstractJpaRepository
import com.emanuelvictor.api.functional.flowcreator.application.persistence.IMapper
import com.emanuelvictor.api.functional.flowcreator.application.persistence.entities.choice.ChoicePersist
import com.emanuelvictor.api.functional.flowcreator.application.persistence.entities.choice.ChoicePersistId
import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.ChoiceId
import com.emanuelvictor.api.functional.flowcreator.domain.repositories.ports.ChoiceRepository
import org.springframework.stereotype.Repository


@Repository // todo must be instanced by beans
class ChoiceJpaRepository(
    val choiceSpringDataJpaRepository: ChoiceSpringDataJpaRepository
) :
    AbstractJpaRepository<Choice, ChoiceId, ChoicePersist, ChoicePersistId>(choiceSpringDataJpaRepository),
    ChoiceRepository {

    override fun listChoicesByOptionsValues(pathFilter: String): List<Choice> {
        return choiceSpringDataJpaRepository.listChoicesByOptionsValues(pathFilter).stream()
            .map { getMapper().persistObjectToDomainObject(it) }.toList();
    }

    override fun getMapper(): IMapper<ChoicePersist, Choice> {
        return ChoiceMapper()
    }
}