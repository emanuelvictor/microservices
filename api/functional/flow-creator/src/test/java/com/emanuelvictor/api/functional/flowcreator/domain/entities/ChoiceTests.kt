package com.emanuelvictor.api.functional.flowcreator.domain.entities

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.Alternative.Companion.SEPARATOR
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.*
import com.emanuelvictor.api.functional.flowcreator.domain.services.AlternativeService
import com.emanuelvictor.api.functional.flowcreator.domain.services.ChoiceService
import com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters.AlternativeRepositoryImpl
import com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters.ChoiceRepositoryImpl
import com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters.OptionRepositoryImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.function.Consumer
import java.util.stream.Collectors

/**
 *
 */
class ChoiceTests {

    private val choiceRepository = ChoiceRepositoryImpl()
    private val optionRepository = OptionRepositoryImpl()

    private val alternativeRepository = AlternativeRepositoryImpl()

    private val alternativeService = AlternativeService(alternativeRepository)

    private val choiceService = ChoiceService(choiceRepository)

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

    /**
     *
     */
    @Test
    fun `Must return the options from alternative`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative("Selecione a unidade?", CompanyOption(value))
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", BranchOption(valueFromUnit))
        val valueFromAttendant = "Maria"
        val attendantSelected = IntermediaryAlternative(unitSelected, "Como foi o atendimento?", PersonOption(valueFromAttendant))
        val choice = Choice(attendantSelected)

        val options = choice.options

        Assertions.assertThat(options.stream().anyMatch { option -> option.identifier == value }).isTrue
        Assertions.assertThat(options.stream().anyMatch { option -> option.identifier == valueFromUnit }).isTrue
        Assertions.assertThat(options.stream().anyMatch { option -> option.identifier == valueFromAttendant }).isTrue
    }

    /**
     *  TODO this test is an integration test
     */
    @Test
    fun `Get all paths from parents`() {
        val value = optionRepository.save(CompanyOption("Bubblemix Tea"))
        val clientSelected = alternativeRepository.save(RootAlternative("Selecione a unidade?", value))
        val valueFromUnit = optionRepository.save(BranchOption("BIG - Foz do Iguaçu"))
        val unitSelected = alternativeRepository.save(IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", true, valueFromUnit))
        val valueFromFirstAttendant = optionRepository.save(PersonOption("Maria"))
        val valueFromSecondAttendant = optionRepository.save(PersonOption("Marcia"))
        val valueFromThirdAttendant = optionRepository.save(PersonOption("Marcelo"))
        val attendantSelected =
            alternativeRepository.save(IntermediaryAlternative(unitSelected, "Selecione os sub atendentes?", true, valueFromFirstAttendant, valueFromSecondAttendant, valueFromThirdAttendant))
        val valueFromFirstSubAttendant = optionRepository.save(PersonOption("Rafael"))
        val valueFromSecondSubAttendant = optionRepository.save(PersonOption("Ricardo"))
        val valueFromThirdSubAttendant = optionRepository.save(PersonOption("Samuel"))
        val subAttendants =
            alternativeRepository.save(IntermediaryAlternative(attendantSelected, "Como foi o atendimento?", valueFromFirstSubAttendant, valueFromSecondSubAttendant, valueFromThirdSubAttendant))
        val subSubAttendantValue = optionRepository.save(PersonOption("subAtendantValue"))
        val subSubAttendent = alternativeRepository.save(IntermediaryAlternative(subAttendants, "nanana", subSubAttendantValue))
        val firstSubSubSubAttendantValue = optionRepository.save(PersonOption("Rosa"))
        val secondSubSubSubAttendantValue = optionRepository.save(PersonOption("Maria"))
        val subSubSubAttendant = alternativeRepository.save(IntermediaryAlternative(subSubAttendent, "nanana", firstSubSubSubAttendantValue, secondSubSubSubAttendantValue))

        val fullPaths = extractPathsFromAlternative(Choice(subSubSubAttendant).path)

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
     * TODO this test is an integration test
     */
    @Test
    fun `Must return the signature from Choice`() {
        val value = optionRepository.save(CompanyOption("Bubblemix Tea"))
        val clientSelected = alternativeRepository.save(RootAlternative("Selecione a unidade?", value))
        val valueFromUnit = optionRepository.save(BranchOption("BIG - Foz do Iguaçu"))
        val unitSelected = alternativeRepository.save(IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", true, valueFromUnit))
        val valueFromFirstAttendant = optionRepository.save(PersonOption("Maria"))
        val valueFromSecondAttendant = optionRepository.save(PersonOption("Marcia"))
        val valueFromThirdAttendant = optionRepository.save(PersonOption("Marcelo"))
        val attendantSelected =
            alternativeRepository.save(IntermediaryAlternative(unitSelected, "Selecione os sub atendentes?", true, valueFromFirstAttendant, valueFromSecondAttendant, valueFromThirdAttendant))
        val valueFromFirstSubAttendant = optionRepository.save(PersonOption("Rafael"))
        val valueFromSecondSubAttendant = optionRepository.save(PersonOption("Ricardo"))
        val valueFromThirdSubAttendant = optionRepository.save(PersonOption("Samuel"))
        val subAttendants =
            alternativeRepository.save(IntermediaryAlternative(attendantSelected, "Como foi o atendimento?", valueFromFirstSubAttendant, valueFromSecondSubAttendant, valueFromThirdSubAttendant))
        val subSubAttendantValue = optionRepository.save(PersonOption("subAtendantValue"))
        val subSubAttendent = alternativeRepository.save(IntermediaryAlternative(subAttendants, "nanana", subSubAttendantValue))
        val firstSubSubSubAttendantValue = optionRepository.save(PersonOption("Rosa"))
        val secondSubSubSubAttendantValue = optionRepository.save(PersonOption("Maria"))
        val subSubSubAttendant = alternativeRepository.save(IntermediaryAlternative(subSubAttendent, "nanana", firstSubSubSubAttendantValue, secondSubSubSubAttendantValue))
        val signatureExpected = value.id.toString() + SEPARATOR + valueFromUnit.id.toString() + SEPARATOR + arrayListOf(
            valueFromFirstAttendant.id,
            valueFromSecondAttendant.id,
            valueFromThirdAttendant.id
        ) + SEPARATOR + arrayListOf(
            valueFromFirstSubAttendant.id,
            valueFromSecondSubAttendant.id,
            valueFromThirdSubAttendant.id
        ) + SEPARATOR + subSubAttendantValue.id + SEPARATOR + arrayListOf(
            firstSubSubSubAttendantValue.id,
            secondSubSubSubAttendantValue.id
        )

        val signatureFromChoice = Choice(subSubSubAttendant).signature

        Assertions.assertThat(signatureFromChoice).isEqualTo(signatureExpected)
    }


    /**
     *  TODO this test is an integration test
     */
    @Test
    fun `Learning Tests`() {
        val choices = ArrayList<Choice>()
        val companyOption = this.optionRepository.save(CompanyOption("Company"))
        val companyAlternative = this.alternativeService.save(RootAlternative("Select the branch", companyOption))
        for (i in 1..3) {
            val branchName = "Branch $i"
            val branchOption = optionRepository.listByValue(branchName).stream().findFirst().orElse(this.optionRepository.save(BranchOption(branchName)))
            val branchAlternative = this.alternativeService.save(IntermediaryAlternative(companyAlternative, "Select the sector", branchOption))
            for (j in 1..3) {
                val sectorName = "Sector $j"
                val sectorOption = optionRepository.listByValue(sectorName).stream().findFirst().orElse(this.optionRepository.save(SectorOption(sectorName)))
                val sectorAlternative = this.alternativeService.save(IntermediaryAlternative(branchAlternative, "Select the attendants", true, sectorOption))
                for (k in 1..3) {
                    val attendantName = "Attendant $k"
                    val attendantAlternative = optionRepository.listByValue(attendantName).stream().findFirst().orElse(this.optionRepository.save(PersonOption(attendantName)))
                    this.alternativeService.save(IntermediaryAlternative(sectorAlternative, "Select the level from service!", this.optionRepository.save(attendantAlternative)))
                }
                val attendantsAlternatives = alternativeService.findChildrenFromAlternativeId(sectorAlternative.id).collect(Collectors.toList())
                for (c in 0 until attendantsAlternatives.count()) {
                    for (k in 1..5) {
                        val levelName = "Level $k"
                        val levelOption = optionRepository.listByValue(levelName).stream().findFirst().orElse(this.optionRepository.save(LevelOption(k)))
                        val levelAlternative = this.alternativeService.save(IntermediaryAlternative(attendantsAlternatives[c], "Thanks!", levelOption))
                        choices.add(choiceService.makeChoice(levelAlternative))
                    }
                }
            }
        }

        choices.forEach(Consumer { println(it.path) })

        println("Count of choices: " + choices.size)
        // todo mabe utilize agregate sql function SUM to countlines, and repeate other fields. It is a subquery
        // TODO and in a parent query (upper query) make an AVG function
        // TODO the user must select the fields of SUM and AVG functions on interface user
        // Do the AVG this fields, and now i add the next filters
        // Faça a média desses campos, e agora eu adicionos os demais filtros pra especializar a busca.
        /// Descobrir como fazer campo mediável
        println(choiceRepository.listChoicesByOptionsValues("Level 1", "Attendant 3", "Branch 1").count())
        println(choiceRepository.listChoicesByOptionsValues("Level 2", "Attendant 3", "Branch 1").count())
        println(choiceRepository.listChoicesByOptionsValues("Level 3", "Attendant 3", "Branch 1").count())
        println(choiceRepository.listChoicesByOptionsValues("Level 4", "Attendant 3", "Branch 1").count())
        println(choiceRepository.listChoicesByOptionsValues("Level 5", "Attendant 3", "Branch 1").count())

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

    /**
     *
     */
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