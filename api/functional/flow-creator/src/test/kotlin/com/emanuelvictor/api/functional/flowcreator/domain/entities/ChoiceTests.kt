package com.emanuelvictor.api.functional.flowcreator.domain.entities

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.Alternative.Companion.SEPARATOR
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.*
import com.emanuelvictor.api.functional.flowcreator.domain.entities.question.Question
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
        val unitiesQuestion = Question("unidades", "Selecione a unidade?")
        val rootValue = "Bubblemix Tea"
        val rootAlternative = RootAlternative(unitiesQuestion, CompanyOption(rootValue))
        val whateverQuestion = Question("whatever", "Whatever?")
        val intermediaryValue = "intermediary value"
        val intermediaryAlternative = IntermediaryAlternative(rootAlternative, whateverQuestion, PersonOption(intermediaryValue))

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
        val unitiesQuestion = Question("unidades", "Selecione a unidade?")
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative(unitiesQuestion, CompanyOption(value))
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val attendantsQuestion = Question("atendentes", "Por quem você foi atendido?")
        val unitSelected = IntermediaryAlternative(clientSelected, attendantsQuestion, BranchOption(valueFromUnit))
        val valueFromAttendant = "Maria"
        val levelQuestion = Question("level", "Como foi o atendimento?")
        val attendantSelected = IntermediaryAlternative(unitSelected, levelQuestion, PersonOption(valueFromAttendant))

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
        val unitiesQuestion = Question("unidades", "Selecione a unidade?")
        val clientSelected = RootAlternative(unitiesQuestion, value)
        val valueFromUnit = BranchOption("BIG - Foz do Iguaçu")
        val attendantsQuestion = Question("atendentes", "Por quem você foi atendido?")
        val unitSelected = IntermediaryAlternative(clientSelected, attendantsQuestion, true, valueFromUnit)
        val valueFromFirstAttendant = PersonOption("Maria")
        val valueFromSecondAttendant = PersonOption("Marcia")
        val valueFromThirdAttendant = PersonOption("Marcelo")
        val subAttendantsQuestion = Question("sub-atendentes", "Selecione os sub atendentes?")
        val attendantSelected = IntermediaryAlternative(unitSelected, subAttendantsQuestion, true, valueFromFirstAttendant, valueFromSecondAttendant, valueFromThirdAttendant)
        val valueFromFirstSubAttendant = PersonOption("Rafael")
        val valueFromSecondSubAttendant = PersonOption("Ricardo")
        val valueFromThirdSubAttendant = PersonOption("Samuel")
        val levelQuestion = Question("level", "Como foi o atendimento?")
        val subAttendants = IntermediaryAlternative(attendantSelected, levelQuestion, valueFromFirstSubAttendant, valueFromSecondSubAttendant, valueFromThirdSubAttendant)
        val subSubAttendantValue = PersonOption("subAtendantValue")
        val nanana = Question("nanana", "nanana")
        val subSubAttendent = IntermediaryAlternative(subAttendants, nanana, subSubAttendantValue)
        val firstSubSubSubAttendantValue = PersonOption("Rosa")
        val secondSubSubSubAttendantValue = PersonOption("Maria")
        val subSubSubAttendant = IntermediaryAlternative(subSubAttendent, nanana, firstSubSubSubAttendantValue, secondSubSubSubAttendantValue)

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
        val unitiesQuestion = Question("unidades", "Selecione a unidade?")
        val clientSelected = RootAlternative(unitiesQuestion, value)
        val valueFromUnit = BranchOption("BIG - Foz do Iguaçu")
        val attendantsQuestion = Question("atendentes", "Por quem você foi atendido?")
        val unitSelected = IntermediaryAlternative(clientSelected, attendantsQuestion, false, valueFromUnit)
        val valueFromFirstAttendant = PersonOption("Maria")
        val subAttendantsQuestion = Question("sub-atendentes", "Selecione os sub atendentes?")
        val attendantSelected = IntermediaryAlternative(unitSelected, subAttendantsQuestion, false, valueFromFirstAttendant)
        val valueFromFirstSubAttendant = PersonOption("Rafael")
        val levelQuestion = Question("level", "Como foi o atendimento?")
        val subAttendants = IntermediaryAlternative(attendantSelected, levelQuestion, valueFromFirstSubAttendant)
        val subSubAttendantValue = PersonOption("subAtendantValue")
        val nanana = Question("nanana", "nanana")
        val subSubAttendent = IntermediaryAlternative(subAttendants, nanana, subSubAttendantValue)
        val firstSubSubSubAttendantValue = PersonOption("Rosa")
        val subSubSubAttendant = IntermediaryAlternative(subSubAttendent, nanana, firstSubSubSubAttendantValue)

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
        val tribeQuestion = Question("unidades", "Selecione a unidade")
        val companyAlternative = RootAlternative(tribeQuestion, company)
        val tribe = SectorOption("Onboard")
        val villageQuestion = Question("village", "Selecione a sua aldeia")
        val tribeAlternative = IntermediaryAlternative(companyAlternative, villageQuestion, tribe)
        val village = SectorOption("Register")
        val leadersQuestion = Question("leaders", "Quem são seus líderes?")
        val villageAlternative = IntermediaryAlternative(tribeAlternative, leadersQuestion, true, village)
        val rafael = PersonOption("Rafael")
        val ricardo = PersonOption("Ricardo")
        val samuel = PersonOption("Samuel")
        val managersQuestion = Question("managers", "Como você avalia seus gestores no quesito 'Empatia'?")
        val managersAlternative = IntermediaryAlternative(villageAlternative, managersQuestion, rafael, ricardo, samuel)
        val level5 = LevelOption(5.toString())
        val thx = Question("thx", "Obrigado!")
        val level5Alternative = IntermediaryAlternative(managersAlternative, thx, level5)
        val signatureExpected =
            companyAlternative.question!!.message + SEPARATOR + tribeAlternative.question!!.message + SEPARATOR + villageAlternative.question!!.message + SEPARATOR + managersAlternative.question!!.message + SEPARATOR + level5Alternative.question!!.message

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