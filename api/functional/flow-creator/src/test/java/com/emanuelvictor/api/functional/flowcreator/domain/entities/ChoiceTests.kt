package com.emanuelvictor.api.functional.flowcreator.domain.entities

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.AbstractAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.AbstractAlternative.Companion.SEPARATOR
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.services.AlternativeService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.function.Consumer

class ChoiceTests {

    @Test
    fun `Instance Of Choice Tests`() {
        val rootValue = "Bubblemix Tea"
        val rootAlternative = RootAlternative("Selecione a unidade?", Option(rootValue))
        val intermediaryValue = "intermediary value"
        val intermediaryAlternative = IntermediaryAlternative(rootAlternative, "Whatever?", Option(intermediaryValue))

        val choice = Choice(intermediaryAlternative)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(rootValue + SEPARATOR + intermediaryValue)
    }

    @Test
    fun `Must return a long path from the choice`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative("Selecione a unidade?", Option(value))
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", Option(valueFromUnit))
        val valueFromAttendant = "Maria"
        val attendantSelected = IntermediaryAlternative(unitSelected, "Como foi o atendimento?", Option(valueFromAttendant))

        val choice = Choice(attendantSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(value + SEPARATOR + valueFromUnit + SEPARATOR + valueFromAttendant)
    }

    /**
     */
    @Test
    fun `Get all paths from parents`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative("Selecione a unidade?", Option(value))
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", true, Option(valueFromUnit))
        val valueFromFirstAttendant = "Maria"
        val valueFromSecondAttendant = "Marcia"
        val valueFromThirdAttendant = "Marcelo"
        val attendantSelected =
            IntermediaryAlternative(unitSelected, "Selecione os sub atendentes?", true, Option(valueFromFirstAttendant), Option(valueFromSecondAttendant), Option(valueFromThirdAttendant))
        val valueFromFirstSubAttendant = "Rafael"
        val valueFromSecondSubAttendant = "Ricardo"
        val valueFromThirdSubAttendant = "Samuel"
        val subAttendants =
            IntermediaryAlternative(attendantSelected, "Como foi o atendimento?", Option(valueFromFirstSubAttendant), Option(valueFromSecondSubAttendant), Option(valueFromThirdSubAttendant))
        val subSubAttendantValue = "subAtendantValue"
        val subSubAttendent = IntermediaryAlternative(subAttendants, "nanana", Option(subSubAttendantValue))
        val firstSubSubSubAttendantValue = "Rosa"
        val secondSubSubSubAttendantValue = "Maria"
        val subSubSubAttendant = IntermediaryAlternative(subSubAttendent, "nanana", Option(firstSubSubSubAttendantValue), Option(secondSubSubSubAttendantValue))

        val fullPaths = extractPathsFromAlternative(Choice(subSubSubAttendant).getPath())

        Assertions.assertThat(fullPaths.size).isEqualTo(18)
        Assertions.assertThat(fullPaths).contains(
            "Bubblemix Tea->BIG - Foz do Iguaçu->Marcia->Rafael->subAtendantValue->Maria",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Marcelo->Ricardo->subAtendantValue->Rosa",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Maria->Ricardo->subAtendantValue->Rosa",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Marcia->Samuel->subAtendantValue->Rosa",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Marcia->Samuel->subAtendantValue->Maria",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Marcelo->Rafael->subAtendantValue->Maria",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Marcelo->Samuel->subAtendantValue->Maria",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Maria->Samuel->subAtendantValue->Rosa",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Maria->Rafael->subAtendantValue->Rosa",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Marcia->Rafael->subAtendantValue->Rosa",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Maria->Samuel->subAtendantValue->Maria",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Marcelo->Ricardo->subAtendantValue->Maria",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Maria->Ricardo->subAtendantValue->Maria",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Marcelo->Rafael->subAtendantValue->Rosa",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Marcia->Ricardo->subAtendantValue->Rosa",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Marcia->Ricardo->subAtendantValue->Maria",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Maria->Rafael->subAtendantValue->Maria",
            "Bubblemix Tea->BIG - Foz do Iguaçu->Marcelo->Samuel->subAtendantValue->Rosa"
        )
    }

    /**
     * TODO tem que ser via ID e não via value, (Será?)
     */
    @Test
    fun `Get all possible sub paths from parents`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative("Selecione a unidade?", Option(value))
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", true, Option(valueFromUnit))
        val valueFromFirstAttendant = "Maria"
        val valueFromSecondAttendant = "Marcia"
        val valueFromThirdAttendant = "Marcelo"
        val attendantSelected =
            IntermediaryAlternative(unitSelected, "Selecione os sub atendentes?", true, Option(valueFromFirstAttendant), Option(valueFromSecondAttendant), Option(valueFromThirdAttendant))
        val valueFromFirstSubAttendant = "Rafael"
        val valueFromSecondSubAttendant = "Ricardo"
        val valueFromThirdSubAttendant = "Samuel"
        val subAttendants =
            IntermediaryAlternative(attendantSelected, "Como foi o atendimento?", Option(valueFromFirstSubAttendant), Option(valueFromSecondSubAttendant), Option(valueFromThirdSubAttendant))
        val subSubAttendantValue = "subAtendantValue"
        val subSubAttendent = IntermediaryAlternative(subAttendants, "nanana", Option(subSubAttendantValue))
        val firstSubSubSubAttendantValue = "Rosa"
        val secondSubSubSubAttendantValue = "Maria"
        val subSubSubAttendant = IntermediaryAlternative(subSubAttendent, "nanana", Option(firstSubSubSubAttendantValue), Option(secondSubSubSubAttendantValue))
        val fullPaths = extractPathsFromAlternative(Choice(subSubSubAttendant).getPath())

        val combinations = extractAllCombinationsFromFullPaths(fullPaths)

        Assertions.assertThat(combinations.size).isEqualTo(1134)
    }

    private fun extractAllCombinationsFromFullPaths(fullPaths: Set<String>): ArrayList<String> {
        val combinations = ArrayList<String>()
        fullPaths.forEach(Consumer { fullPath ->
            val allNodesInOrder = fullPath.split(SEPARATOR)
            val combinationsIndexes = AlternativeService.generate(allNodesInOrder.size)
            combinationsIndexes.forEach(Consumer { combinationIndex ->
                val subPath: StringBuilder = StringBuilder()
                for ((index) in combinationIndex.withIndex()) {
                    subPath.append(allNodesInOrder[combinationIndex[index]])
                    if (index != combinationIndex.size - 1)
                        subPath.append(SEPARATOR)
                }
                combinations.add(subPath.toString())
            })
        })
        return combinations
    }

    private fun extractPathsFromAlternative(pathFromAlternative: String): HashSet<String> {
        val paths = HashSet<String>()
        if (pathFromAlternative.contains("["))
            pathFromAlternative.substring(pathFromAlternative.indexOf("[") + 1, pathFromAlternative.indexOf("]"))
                .split(", ")
                .forEach(Consumer {
                    val extracted = (pathFromAlternative.substring(0, pathFromAlternative.indexOf("[")) + it + pathFromAlternative.substring(pathFromAlternative.indexOf("]") + 1))
                    if (!extracted.contains("["))
                        paths.add(extracted)
                    paths.addAll(extractPathsFromAlternative(extracted))
                })
        return paths
    }

}