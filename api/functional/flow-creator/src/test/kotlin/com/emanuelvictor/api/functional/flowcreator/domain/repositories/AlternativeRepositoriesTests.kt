package com.emanuelvictor.api.functional.flowcreator.domain.repositories

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.CompanyOption
import com.emanuelvictor.api.functional.flowcreator.domain.entities.question.Question
import com.emanuelvictor.api.functional.flowcreator.infrastructure.helpers.PopulateHelper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
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
class AlternativeRepositoriesTests(
    @Autowired private val optionRepository: OptionRepository,
    @Autowired private val questionRepository: QuestionRepository,
    @Autowired private val rootAlternativeRepository: RootAlternativeRepository,
    @Autowired private val alternativeRepository: AlternativeRepository,
    @Autowired private val intermediaryAlternativeRepository: IntermediaryAlternativeRepository,
    @Autowired private val populateHelper: PopulateHelper
) {

    /**
     *
     */
    @BeforeEach
    fun beforeEach() {
        populateHelper.populateBubbleMixData()
    }

    /**
     *
     */
    @Test
    @Sql("/dataset/truncate-all-tables.sql")
    fun `Must verify if the database is populated`() {
        Assertions.assertThat(alternativeRepository.findAll().count()).isEqualTo(17)
        Assertions.assertThat(rootAlternativeRepository.findAll().count()).isEqualTo(1)
        Assertions.assertThat(intermediaryAlternativeRepository.findAll().count()).isEqualTo(16)

        val rootAlternative = rootAlternativeRepository.findAll().stream().findFirst().orElseThrow()
        val unities = intermediaryAlternativeRepository.findAllByPreviousId(rootAlternative.id!!)

        Assertions.assertThat(unities.count()).isEqualTo(2)
    }

    /**
     *
     */
    @Test
    @Sql("/dataset/truncate-all-tables.sql")
    fun `Must verify the children of Root`() {
        val rootAlternative = rootAlternativeRepository.findAll().stream().findFirst().orElseThrow()
        val unities = intermediaryAlternativeRepository.findAllByPreviousId(rootAlternative.id!!)

        Assertions.assertThat(unities.count()).isEqualTo(2)
    }

    /**
     *
     */
    @Test
    @Sql("/dataset/truncate-all-tables.sql")
    fun `Must save IntermediaryAlternative`() {
        val unitiesQuestion = questionRepository.save(Question("unities", "Selecione a unidade?"))
        val value = "BubbleFresh Tea"
        val clientSelected = alternativeRepository.save(RootAlternative(unitiesQuestion, optionRepository.save(CompanyOption(value))))
        val attendantsQuestion = questionRepository.save(Question("attendants", "Por quem você foi atendido?"))
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, attendantsQuestion, optionRepository.findByIdentifier(valueFromUnit).orElseGet { optionRepository.save(CompanyOption(valueFromUnit)) })
        Assertions.assertThat(unitSelected.id).isNull()

        alternativeRepository.save(unitSelected)

        Assertions.assertThat(unitSelected.id).isNotNull
    }

    /**
     *
     */
    @Test
    @Sql("/dataset/truncate-all-tables.sql")
    fun `Must find Alternatvie by path`() {
        val unitiesQuestion = questionRepository.save(Question("unities", "Selecione a unidade?"))
        val companyOption  = optionRepository.save(optionRepository.save(CompanyOption("Company")))
        val companyAlternative = alternativeRepository.save(RootAlternative(unitiesQuestion, companyOption))
        val branchOption =  optionRepository.save(optionRepository.save(CompanyOption("Branch")))
        val attendantsQuestion = questionRepository.save(Question("attendants", "Por quem você foi atendido?"))
        val alternativeExpected = alternativeRepository.save(IntermediaryAlternative(companyAlternative, attendantsQuestion, branchOption))

        val alternative = alternativeRepository.findByPath(alternativeExpected.path!!).orElseThrow()

        Assertions.assertThat(alternative.id).isEqualTo(alternativeExpected.id)
    }

    /**
     *
     */
    @Test
    @Sql("/dataset/truncate-all-tables.sql")
    fun `Must save RootAlternative`() {
        val unitiesQuestion = questionRepository.save(Question("unities", "Selecione a unidade?"))
        val value = "BubbleFresh Tea"
        val clientSelected = RootAlternative(unitiesQuestion, optionRepository.save(CompanyOption(value)))
        Assertions.assertThat(clientSelected.id).isNull()

        alternativeRepository.save(clientSelected)

        Assertions.assertThat(clientSelected.id).isNotNull
    }
}