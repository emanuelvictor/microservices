package com.emanuelvictor.api.functional.flowcreator.domain.entities

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.AbstractAlternative.Companion.SEPARATOR
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.function.Consumer

class ChoiceTests {

    @Test
    fun `Instance Of Choice Tests`() {
        val rootValue = "Bubblemix Tea"
        val rootAlternative = RootAlternative( "Selecione a unidade?", rootValue)
        val intermediaryValue = "intermediary value"
        val intermediaryAlternative = IntermediaryAlternative(rootAlternative,  "Whatever?", intermediaryValue)

        val choice = Choice(intermediaryAlternative)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(rootValue + SEPARATOR + intermediaryValue)
    }

    @Test
    fun `Must return a long path from the choice`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative( "Selecione a unidade?", value)
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", valueFromUnit)
        val valueFromAttendant = "Maria"
        val attendantSelected = IntermediaryAlternative(unitSelected, "Como foi o atendimento?", valueFromAttendant)

        val choice = Choice(attendantSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(value + SEPARATOR + valueFromUnit + SEPARATOR + valueFromAttendant)
    }

    /**
     * TODO AQUI É O B.O
     */
    @Test
    fun `Get all ordered nodes from parents`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative("Selecione a unidade?", value)
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", true, valueFromUnit)
        val valueFromFirstAttendant = "Maria"
        val valueFromSecondAttendant = "Marcia"
        val valueFromThirdAttendant = "Marcelo"
        val attendantSelected = IntermediaryAlternative(unitSelected, "Selecione os sub atendentes?", true, valueFromFirstAttendant, valueFromSecondAttendant, valueFromThirdAttendant)
        val valueFromFirstSubAttendant = "Rafael"
        val valueFromSecondSubAttendant = "Ricardo"
        val valueFromThirdSubAttendant = "Samuel"
        val subAttendants = IntermediaryAlternative(attendantSelected, "Como foi o atendimento?", valueFromFirstSubAttendant, valueFromSecondSubAttendant, valueFromThirdSubAttendant)
        val subSubAttendantValue = "subAtendantValue"
        val subSubAttendent = IntermediaryAlternative(subAttendants, "nanana", subSubAttendantValue)
        val firstSubSubSubAttendantValue = "Rosa"
        val secondSubSubSubAttendantValue = "Maria"
        val subSubSubAttendant = IntermediaryAlternative(subSubAttendent, "nanana", firstSubSubSubAttendantValue, secondSubSubSubAttendantValue)

        val paths = extractPathsFromAlternative(Choice(subSubSubAttendant).getPath())

        Assertions.assertThat(paths.size).isEqualTo(18)
        Assertions.assertThat(paths).contains(
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