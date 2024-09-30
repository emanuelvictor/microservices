package com.emanuelvictor.api.functional.flowcreator.application.persistence.adapters.choice

import com.emanuelvictor.api.functional.flowcreator.application.persistence.entities.choice.ChoicePersist
import com.emanuelvictor.api.functional.flowcreator.application.persistence.entities.choice.ChoicePersistId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ChoiceSpringDataJpaRepository : JpaRepository<ChoicePersist, ChoicePersistId> {

    /**
     * TODO remove vararg
     */
    @Query("select c from Choice c where FILTER(:pathFilter, c.path)")
    fun listChoicesByOptionsValues(pathFilter: String): List<ChoicePersist>
}