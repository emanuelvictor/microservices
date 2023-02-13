package com.emanuelvictor.api.functional.flowcreator.domain.services

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.*
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.ChoiceRepository
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.OptionRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.function.Consumer
import java.util.stream.Collectors

/**
 *
 */
@SpringBootTest
class ChoiceServiceTests(
    @Autowired private val alternativeService: AlternativeService,
    @Autowired private val optionRepository: OptionRepository,
    @Autowired private val choiceRepository: ChoiceRepository,
    @Autowired private val choiceService: ChoiceService
) {

    /**
     *
     */
    @Test
    fun `Learning Tests`() {
        val choices = ArrayList<Choice>()
        val companyOption = CompanyOption("Company")
        val companyAlternative = this.alternativeService.save(RootAlternative("Select the branch", companyOption))
        for (i in 1..3) {
            val branchName = "Branch $i"
            val branchOption = optionRepository.listByValue(branchName).stream().findFirst().orElse(BranchOption(branchName))
            val branchAlternative = this.alternativeService.save(IntermediaryAlternative(companyAlternative, "Select the sector", branchOption))
            for (j in 1..3) {
                val sectorName = "Sector $j"
                val sectorOption = optionRepository.listByValue(sectorName).stream().findFirst().orElse(SectorOption(sectorName))
                val sectorAlternative = this.alternativeService.save(IntermediaryAlternative(branchAlternative, "Select the attendants", true, sectorOption))
                for (k in 1..3) {
                    val attendantName = "Attendant $k"
                    val attendantAlternative = optionRepository.listByValue(attendantName).stream().findFirst().orElse(PersonOption(attendantName))
                    this.alternativeService.save(IntermediaryAlternative(sectorAlternative, "Select the level from service!", attendantAlternative))
                }
                val attendantsAlternatives = alternativeService.findChildrenFromAlternativeId(sectorAlternative.id).collect(Collectors.toList())
                for (c in 0 until attendantsAlternatives.count()) {
                    for (k in 1..5) {
                        val levelName = "Level $k"
                        val levelOption = optionRepository.listByValue(levelName).stream().findFirst().orElse(LevelOption(k))
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
}