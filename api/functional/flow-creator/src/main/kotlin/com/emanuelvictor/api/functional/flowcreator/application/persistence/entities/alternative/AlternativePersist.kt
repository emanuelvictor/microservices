package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.option.OptionAlternativePersist
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.OptionPersist
import com.emanuelvictor.api.functional.flowcreator.domain.entities.question.QuestionPersist
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.util.stream.Collectors

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class AlternativePersist(question: QuestionPersist, nextIsMultipleChoice: Boolean = false, vararg options: OptionPersist) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    open var id: Long? = null

    @Column(nullable = false)
    open var nextIsMultipleChoice: Boolean? = nextIsMultipleChoice

    @OneToMany(
        targetEntity = OptionAlternativePersist::class,
        mappedBy = "alternative",
        fetch = FetchType.EAGER,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var optionsAlternatives: List<OptionAlternativePersist>? = options.map { OptionAlternativePersist(it, this) }.toList()

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id")
    var question: QuestionPersist? = question

    @Column(nullable = false, unique = true)
    open var signature: String? = null

    @Column(nullable = false, unique = true)
    open var path: String? = null

}