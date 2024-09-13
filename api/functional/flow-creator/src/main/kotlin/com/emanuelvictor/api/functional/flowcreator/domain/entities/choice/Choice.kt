package com.emanuelvictor.api.functional.flowcreator.domain.entities.choice

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.Alternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.Alternative.Companion.SEPARATOR
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.generic.Entity
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.function.Consumer


/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
@jakarta.persistence.Entity
class Choice(alternative: IntermediaryAlternative) : Entity<ChoiceId>() {

    @Column(nullable = false)
    val date: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    var path: String? = null

    @Column(nullable = false)
    var header: String? = null

    @ElementCollection(fetch = FetchType.EAGER)
    var splittedPaths: List<String> = ArrayList()

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alternative_id", nullable = false)
    var alternative: IntermediaryAlternative? = alternative

    init {
        this.header = getHeaderFromAlternative(alternative, StringBuilder()).toString()
        this.path = alternative.path
        this.splittedPaths = splitPathsFromPath(this.path!!)
    }

    /**
     *
     */
    private fun getHeaderFromAlternative(alternative: Alternative, header: StringBuilder): StringBuilder {

        if (alternative is IntermediaryAlternative)
            getHeaderFromAlternative(alternative.previous!!, header)

        if (alternative is RootAlternative)
            header.append(alternative.question!!.message)
        else
            header.append(SEPARATOR).append(alternative.question!!.message)

        return header
    }

    /**
     * Extract all splitted paths from alternatives.
     * Example:     Company -> Branch -> [Attendant1, Attendant2] -> level 1
     *              to
     *              Company -> Branch -> Attendant1 -> level 1
     *              Company -> Branch -> Attendant2 -> level 1
     */
    private fun splitPathsFromPath(pathFromChoice: String): List<String> {
        val paths = HashSet<String>()
        if (pathFromChoice.contains("["))
            pathFromChoice.substring(pathFromChoice.indexOf("[") + 1, pathFromChoice.indexOf("]"))
                .split(", ")
                .forEach(Consumer {
                    val extracted = (pathFromChoice.substring(0, pathFromChoice.indexOf("[")) + it + pathFromChoice.substring(pathFromChoice.indexOf("]") + 1))
                    if (!extracted.contains("["))
                        paths.add(extracted)
                    paths.addAll(splitPathsFromPath(extracted))
                })
        else
            paths.add(pathFromChoice)
        return paths.toList()
    }

}
