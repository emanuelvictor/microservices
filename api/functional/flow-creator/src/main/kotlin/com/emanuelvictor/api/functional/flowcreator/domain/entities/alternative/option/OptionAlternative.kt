package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.option

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.Alternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["alternative_id", "option_id"])])
class OptionAlternative() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null

    /**
     *
     */
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "alternative_id")
    var alternative: Alternative? = null

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "option_id")
    var option: Option? = null

    constructor(option: Option, alternative: Alternative) : this() {
        this.option = option
        this.alternative = alternative
    }
}