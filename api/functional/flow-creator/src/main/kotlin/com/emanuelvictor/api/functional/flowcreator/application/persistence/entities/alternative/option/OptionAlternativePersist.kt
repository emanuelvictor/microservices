package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.option

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.AlternativePersist
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.OptionPersist
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["alternative_id", "option_id"])])
class OptionAlternativePersist(option: OptionPersist, alternative: AlternativePersist) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "alternative_id")
    var alternative: AlternativePersist? = alternative

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "option_id")
    var option: OptionPersist? = option

}