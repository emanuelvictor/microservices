package com.emanuelvictor.api.functional.flowcreator.domain.entities

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.Alternative.Companion.SEPARATOR
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.*
import com.emanuelvictor.api.functional.flowcreator.domain.services.AlternativeService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.function.Consumer

/**
 *
 */
class ChoiceTests {

    /**
     *
     */
    @Test
    fun `Instance Of Choice Tests`() {
        val rootValue = "Bubblemix Tea"
        val rootAlternative = RootAlternative("Selecione a unidade?", CompanyOption(rootValue))
        val intermediaryValue = "intermediary value"
        val intermediaryAlternative = IntermediaryAlternative(rootAlternative, "Whatever?", PersonOption(intermediaryValue))

        val choice = Choice(intermediaryAlternative)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.path).isNotNull
        Assertions.assertThat(choice.path).isEqualTo(rootValue + SEPARATOR + intermediaryValue)
        Assertions.assertThat(choice.date).isNotNull
    }

    /**
     *
     */
    @Test
    fun `Must return a long path from the choice`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative("Selecione a unidade?", CompanyOption(value))
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", BranchOption(valueFromUnit))
        val valueFromAttendant = "Maria"
        val attendantSelected = IntermediaryAlternative(unitSelected, "Como foi o atendimento?", PersonOption(valueFromAttendant))

        val choice = Choice(attendantSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.path).isNotNull
        Assertions.assertThat(choice.path).isEqualTo(value + SEPARATOR + valueFromUnit + SEPARATOR + valueFromAttendant)
        Assertions.assertThat(choice.date).isNotNull
    }

//    /**
//     *
//     */
//    @Test
//    fun `Must return the options from alternative`() {
//        val value = "Bubblemix Tea"
//        val clientSelected = RootAlternative("Selecione a unidade?", CompanyOption(value))
//        val valueFromUnit = "BIG - Foz do Iguaçu"
//        val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", BranchOption(valueFromUnit))
//        val valueFromAttendant = "Maria"
//        val attendantSelected = IntermediaryAlternative(unitSelected, "Como foi o atendimento?", PersonOption(valueFromAttendant))
//        val choice = Choice(attendantSelected)
//
//        val options = choice.options
//
//        Assertions.assertThat(options.stream().anyMatch { option -> option.identifier == value }).isTrue
//        Assertions.assertThat(options.stream().anyMatch { option -> option.identifier == valueFromUnit }).isTrue
//        Assertions.assertThat(options.stream().anyMatch { option -> option.identifier == valueFromAttendant }).isTrue
//    }

    /**
     *
     */
    @Test
    fun `Get all paths from parents`() {
        val value = CompanyOption("Bubblemix Tea")
        val clientSelected = RootAlternative("Selecione a unidade?", value)
        val valueFromUnit = BranchOption("BIG - Foz do Iguaçu")
        val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", true, valueFromUnit)
        val valueFromFirstAttendant = PersonOption("Maria")
        val valueFromSecondAttendant = PersonOption("Marcia")
        val valueFromThirdAttendant = PersonOption("Marcelo")
        val attendantSelected = IntermediaryAlternative(unitSelected, "Selecione os sub atendentes?", true, valueFromFirstAttendant, valueFromSecondAttendant, valueFromThirdAttendant)
        val valueFromFirstSubAttendant = PersonOption("Rafael")
        val valueFromSecondSubAttendant = PersonOption("Ricardo")
        val valueFromThirdSubAttendant = PersonOption("Samuel")
        val subAttendants = IntermediaryAlternative(attendantSelected, "Como foi o atendimento?", valueFromFirstSubAttendant, valueFromSecondSubAttendant, valueFromThirdSubAttendant)
        val subSubAttendantValue = PersonOption("subAtendantValue")
        val subSubAttendent = IntermediaryAlternative(subAttendants, "nanana", subSubAttendantValue)
        val firstSubSubSubAttendantValue = PersonOption("Rosa")
        val secondSubSubSubAttendantValue = PersonOption("Maria")
        val subSubSubAttendant = IntermediaryAlternative(subSubAttendent, "nanana", firstSubSubSubAttendantValue, secondSubSubSubAttendantValue)

        val fullPaths = Choice(subSubSubAttendant).splittedPaths

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
     *
     */
    @Test
    fun `Get all paths from parents without multiple choices`() {
        val value = CompanyOption("Bubblemix Tea")
        val clientSelected = RootAlternative("Selecione a unidade?", value)
        val valueFromUnit = BranchOption("BIG - Foz do Iguaçu")
        val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", false, valueFromUnit)
        val valueFromFirstAttendant = PersonOption("Maria")
        val attendantSelected = IntermediaryAlternative(unitSelected, "Selecione os sub atendentes?", false, valueFromFirstAttendant)
        val valueFromFirstSubAttendant = PersonOption("Rafael")
        val subAttendants = IntermediaryAlternative(attendantSelected, "Como foi o atendimento?", valueFromFirstSubAttendant)
        val subSubAttendantValue = PersonOption("subAtendantValue")
        val subSubAttendent = IntermediaryAlternative(subAttendants, "nanana", subSubAttendantValue)
        val firstSubSubSubAttendantValue = PersonOption("Rosa")
        val subSubSubAttendant = IntermediaryAlternative(subSubAttendent, "nanana", firstSubSubSubAttendantValue)

        val fullPaths = Choice(subSubSubAttendant).splittedPaths

        Assertions.assertThat(fullPaths.size).isEqualTo(1)
        Assertions.assertThat(fullPaths).contains("Bubblemix Tea->BIG - Foz do Iguaçu->Maria->Rafael->subAtendantValue->Rosa")
    }

    /**
     *
     */
    @Test
    fun `Must back header of choice`() {
        val company = CompanyOption("Fiserv")
        val companyAlternative = RootAlternative("Selecione a tribo", company)
        val tribe = SectorOption("Onboard")
        val tribeAlternative = IntermediaryAlternative(companyAlternative, "Selecione a sua aldeia", tribe)
        val village = SectorOption("Register")
        val villageAlternative = IntermediaryAlternative(tribeAlternative, "Quem são seus líderes?", true, village)
        val rafael = PersonOption("Rafael")
        val ricardo = PersonOption("Ricardo")
        val samuel = PersonOption("Samuel")
        val managersAlternative = IntermediaryAlternative(villageAlternative, "Como você avalia seus gestores no quesito 'Empatia'?", rafael, ricardo, samuel)
        val level5 = LevelOption(5.toString())
        val level5Alternative = IntermediaryAlternative(managersAlternative, "Obrigado!", level5)
        val signatureExpected =
            companyAlternative.messageToNext + SEPARATOR + tribeAlternative.messageToNext + SEPARATOR + villageAlternative.messageToNext + SEPARATOR + managersAlternative.messageToNext + SEPARATOR + level5Alternative.messageToNext

        val headerFromChoice = Choice(level5Alternative).header

        Assertions.assertThat(headerFromChoice).isEqualTo(signatureExpected)
    }


    /**
     *
     */
    @Deprecated("This algorithm is deprecated")
    private fun extractAllCombinationsFromFullPaths(fullPaths: Set<String>): ArrayList<String> {
        val combinations = ArrayList<String>()
        fullPaths.forEach(Consumer { fullPath ->
            val allNodesInOrder = fullPath.split(SEPARATOR)
            val combinationsIndexes = AlternativeService.generateCombinations(allNodesInOrder.size)
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
}