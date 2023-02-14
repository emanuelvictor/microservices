package com.emanuelvictor.api.functional.flowcreator.domain.entities

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.AbstractAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.AbstractAlternative.Companion.SEPARATOR
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option
import io.github.emanuelvictor.commons.persistence.generic.PersistentEntity
import java.lang.StringBuilder
import java.time.LocalDateTime
import java.util.function.Consumer

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
class Choice(val alternative: IntermediaryAlternative) : PersistentEntity() {

    val date: LocalDateTime = LocalDateTime.now()

    val options: HashSet<Option> = HashSet()
        get() {
            getOptionFromAlternative(alternative, field)
            return field
        }

    /**
     *
     */
    val signature: String
        get() = alternative.signature

    /**
     *
     */
    val path: String
        get() = alternative.path

    /**
     *
     */
    val header: String
        get() = getHeaderFromAlternative(this.alternative, StringBuilder()).toString()

    /**
     *
     */
    private fun getHeaderFromAlternative(abstractAlternative: AbstractAlternative, header: StringBuilder): StringBuilder {

        if (abstractAlternative is IntermediaryAlternative)
            getHeaderFromAlternative(abstractAlternative.previous, header)

        if (abstractAlternative is RootAlternative)
            header.append(abstractAlternative.messageToNext)
        else
            header.append(SEPARATOR).append(abstractAlternative.messageToNext)

        return header
    }

    /**
     * Extract all splitted paths from alternatives.
     * Example:     Company -> Branch -> [Attendant1, Attendant2] -> level 1
     *              to
     *              Company -> Branch -> Attendant1 -> level 1
     *              Company -> Branch -> Attendant2 -> level 1
     */
    val splittedPaths: HashSet<String>
        get() = splitPathsFromPath(this.path)

    /**
     *
     */
    private fun getOptionFromAlternative(abstractAlternative: AbstractAlternative, options: HashSet<Option>) {
        if (abstractAlternative is IntermediaryAlternative)
            getOptionFromAlternative(abstractAlternative.previous, options)
        options.addAll(abstractAlternative.options)
    }

    /**
     * Extract all splitted paths from alternatives.
     * Example:     Company -> Branch -> [Attendant1, Attendant2] -> level 1
     *              to
     *              Company -> Branch -> Attendant1 -> level 1
     *              Company -> Branch -> Attendant2 -> level 1
     */
    private fun splitPathsFromPath(pathFromChoice: String): HashSet<String> {
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
        return paths
    }
}
