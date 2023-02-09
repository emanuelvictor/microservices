package com.emanuelvictor.api.functional.flowcreator.domain.ports

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.*
import com.emanuelvictor.api.functional.flowcreator.domain.services.AlternativeService
import com.emanuelvictor.api.functional.flowcreator.domain.services.ChoiceService
import com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters.AlternativeRepositoryImpl
import com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters.ChoiceRepositoryImpl
import com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters.OptionRepositoryImpl
import com.emanuelvictor.api.functional.flowcreator.infrastructure.helprs.PopulateHelper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.function.Consumer
import java.util.stream.Collectors

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
class ChoiceRepositoryTests {

    private val optionRepository = OptionRepositoryImpl();

    private val choiceRepository = ChoiceRepositoryImpl();

    private val choiceService = ChoiceService(choiceRepository)

    private val alternativeRepository = AlternativeRepositoryImpl()

    private val alternativeService = AlternativeService(alternativeRepository);

    /**
     * TODO we need to save in some place the individual graphs
     */
    @Test
    fun `Must list choices by values`() {
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

        Assertions.assertThat(choiceRepository.listChoicesByOptionsValues("Attendant 1", "Branch 1").count()).isEqualTo(60)
    }
}