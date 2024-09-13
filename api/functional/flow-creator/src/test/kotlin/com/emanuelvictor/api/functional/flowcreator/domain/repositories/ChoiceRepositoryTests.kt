package com.emanuelvictor.api.functional.flowcreator.domain.repositories

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.ChoiceId
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.*
import com.emanuelvictor.api.functional.flowcreator.domain.entities.question.Question
import com.emanuelvictor.api.functional.flowcreator.domain.services.AlternativeService
import com.emanuelvictor.api.functional.flowcreator.domain.services.ChoiceService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */

@SpringBootTest
class ChoiceRepositoryTests(
    @Autowired private val alternativeService: AlternativeService,
    @Autowired private val questionRepository: QuestionRepository,
    @Autowired private val optionRepository: OptionRepository,
    @Autowired private val choiceRepository: ChoiceRepository,
    @Autowired private val choiceService: ChoiceService
) {

    /**
     * TODO we need to save in some place the individual graphs
     */
    @Test
    @Sql("/dataset/truncate-all-tables.sql")
    fun `Must list choices by values`() {

        val selectBranch = questionRepository.save(Question("select-the-branch","Select the branch"))
        val selectSector = questionRepository.save(Question("select-the-sector","Select the sector"))
        val selectAttendants = questionRepository.save(Question("select-attendants","Select the attendants"))
        val selectLevel = questionRepository.save(Question("select-level","Select the level from service!"))
        val thx = questionRepository.save(Question("thx","Thanks!"))

        val choices = ArrayList<Choice>()
        val companyOption = this.optionRepository.save(CompanyOption("Company"))
        val companyAlternative = this.alternativeService.save(RootAlternative(selectBranch, companyOption))
        for (i in 1..3) {
            val branchName = "Branch $i"
            val branchOption = optionRepository.listByValue(branchName).stream().findFirst().orElseGet { this.optionRepository.save(BranchOption(branchName)) }
            val branchAlternative = this.alternativeService.save(IntermediaryAlternative(companyAlternative, selectSector, branchOption))
            for (j in 1..3) {
                val sectorName = "Sector $j"
                val sectorOption = optionRepository.listByValue(sectorName).stream().findFirst().orElseGet { this.optionRepository.save(SectorOption(sectorName)) }
                val sectorAlternative = this.alternativeService.save(IntermediaryAlternative(branchAlternative, selectAttendants, true, sectorOption))
                for (k in 1..3) {
                    val attendantName = "Attendant $k"
                    val attendantAlternative = optionRepository.listByValue(attendantName).stream().findFirst().orElseGet { this.optionRepository.save(PersonOption(attendantName)) }
                    this.alternativeService.save(IntermediaryAlternative(sectorAlternative, selectLevel, this.optionRepository.save(attendantAlternative)))
                }
                val attendantsAlternatives = alternativeService.findChildrenFromAlternativeId(sectorAlternative.id).stream().toList()
                for (c in 0 until attendantsAlternatives.count()) {
                    for (k in 1..5) {
                        val levelName = "Level $k"
                        val levelOption = optionRepository.listByValue(levelName).stream().findAny().orElseGet { this.optionRepository.save(LevelOption(levelName)) }
                        val levelAlternative = this.alternativeService.save(IntermediaryAlternative(attendantsAlternatives[c], thx, levelOption))
                        choices.add(choiceService.choose(levelAlternative))
                    }
                }
            }
        }

        Assertions.assertThat(choiceRepository.listChoicesByOptionsValues("Branch 1, Attendant 1,Level 1").count()).isEqualTo(147)
        var choice = choiceRepository.listChoicesByOptionsValues("Branch 1, Attendant 1,Level 1")[0];
        Assertions.assertThat(choiceRepository.findById(ChoiceId(choice.identifier.id))).isNotNull
        val oldId = choice.identifier.id

        val intermediaryAlternativeTest = this.alternativeService.save(IntermediaryAlternative(companyAlternative, selectBranch))
        Assertions.assertThat(choice.alternative?.path).isNotEqualTo(intermediaryAlternativeTest.path)

        choice = Choice(intermediaryAlternativeTest as IntermediaryAlternative)
        choice.identifier = ChoiceId(oldId)

        choice = choiceRepository.save(choice)
        Assertions.assertThat(choice.alternative?.path).isEqualTo(intermediaryAlternativeTest.path)

        choice = choiceRepository.findById(ChoiceId(choice.identifier.id)).get()
//        choice = choiceRepository.findById(choice.identifier as ChoiceId).get() // TODO wtf it does not work
        Assertions.assertThat(choice.alternative?.path).isEqualTo(intermediaryAlternativeTest.path)
    }
}