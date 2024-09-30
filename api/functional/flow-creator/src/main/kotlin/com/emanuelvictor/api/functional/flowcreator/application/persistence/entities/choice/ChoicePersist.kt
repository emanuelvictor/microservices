package com.emanuelvictor.api.functional.flowcreator.application.persistence.entities.choice

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternativePersist
import com.emanuelvictor.api.functional.flowcreator.domain.entities.generic.Entity
import com.emanuelvictor.api.functional.flowcreator.domain.entities.generic.UniqueIdentifier
import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
@jakarta.persistence.Entity
class ChoicePersist(alternative: IntermediaryAlternativePersist) : Entity<ChoicePersistId>() {

    @Column(nullable = false)
    var date: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    var path: String? = null

    @Column(nullable = false)
    var header: String? = null

    @ElementCollection(fetch = FetchType.EAGER)
    var splittedPaths: List<String> = ArrayList()

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alternative_id", nullable = false)
    var alternative: IntermediaryAlternativePersist = alternative
}

@Embeddable
class ChoicePersistId(id: String?) : UniqueIdentifier<String>(id) {

}
