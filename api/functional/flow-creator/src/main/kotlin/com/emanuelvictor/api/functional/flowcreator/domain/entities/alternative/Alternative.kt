package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.option.OptionAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option
import jakarta.persistence.*
import java.util.stream.Collectors

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class Alternative(@Column(nullable = false) open var messageToNext: String, @Column(nullable = false) open var nextIsMultipleChoice: Boolean, options: List<Option>) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    open var id: Long? = null

    @OneToMany(targetEntity = OptionAlternative::class, mappedBy = "alternative", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    open var optionsAlternatives: List<OptionAlternative>? = null

    val options: MutableList<Option?>
        get() {
            return optionsAlternatives!!.stream().map { it.option }.toList()
        }

    /**
     * TODO make a unique number
     */
    @Column(nullable = false, unique = true)
    open var signature: String? = null

    @Column(nullable = false, unique = true)
    open var path: String? = null

    init {
        this.optionsAlternatives = options.stream().map { OptionAlternative(it, this) }.toList()
    }

    constructor(messageToNext: String, nextIsMultipleChoice: Boolean, vararg options: Option) : this(messageToNext, nextIsMultipleChoice, options.toList())

    open fun generatePath(): String {
        this.path = optionsValuesToString()
        return this.path!!
    }

    open fun generateSignature() {
        this.signature = optionsIdsToString()
    }

    /**
     * @param {@link Set<String>} The set who will be converted to String.
     * @return {@link String} The set converted to String
     */
    open fun optionsValuesToString(): String {
        if (options.size == 1) {
            return options.first()!!.identifier
        }
        return options.toList().stream().map { it!!.identifier }.collect(Collectors.toList()).toString()
    }

    /**
     * @param {@link Set<String>} The set who will be converted to String.
     * @return {@link String} The set converted to String
     */
    open fun optionsIdsToString(): String {
        if (options.size == 1) {
            return options.first()!!.id.toString()
        }
        return options.toList().stream().map { it!!.id }.collect(Collectors.toList()).toString()
    }

    internal companion object {
        const val SEPARATOR = "->"
    }
}