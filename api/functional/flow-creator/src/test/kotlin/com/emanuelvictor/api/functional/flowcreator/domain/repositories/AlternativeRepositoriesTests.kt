package com.emanuelvictor.api.functional.flowcreator.domain.repositories

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.CompanyOption
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
    @Autowired private val rootAlternativeRepository: RootAlternativeRepository,
    @Autowired private val abstractAlternativeRepository: AbstractAlternativeRepository,
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
        Assertions.assertThat(abstractAlternativeRepository.findAll().count()).isEqualTo(17)
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
        val value = "BubbleFresh Tea"
        val clientSelected = abstractAlternativeRepository.save(RootAlternative("Selecione a unidade?", optionRepository.save(CompanyOption(value))))
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", optionRepository.findByIdentifier(valueFromUnit).orElseGet { optionRepository.save(CompanyOption(valueFromUnit)) })
        Assertions.assertThat(unitSelected.id).isNull()

        abstractAlternativeRepository.save(unitSelected)

        Assertions.assertThat(unitSelected.id).isNotNull
    }

    /**
     *
     */
    @Test
    @Sql("/dataset/truncate-all-tables.sql")
    fun `Must find AbstractAlternatvie by path`() {
        val companyOption  = optionRepository.save(optionRepository.save(CompanyOption("Company")))
        val companyAlternative = abstractAlternativeRepository.save(RootAlternative("Selecione a unidade?", companyOption))
        val branchOption =  optionRepository.save(optionRepository.save(CompanyOption("Branch")))
        val alternativeExpected = abstractAlternativeRepository.save(IntermediaryAlternative(companyAlternative, "Por quem você foi atendido?", branchOption))

        val alternative = abstractAlternativeRepository.findByPath(alternativeExpected.path!!).orElseThrow()

        Assertions.assertThat(alternative.id).isEqualTo(alternativeExpected.id)
    }

    /**
     *
     */
    @Test
    @Sql("/dataset/truncate-all-tables.sql")
    fun `Must save RootAlternative`() {
        val value = "BubbleFresh Tea"
        val clientSelected = RootAlternative("Selecione a unidade?", optionRepository.save(CompanyOption(value)))
        Assertions.assertThat(clientSelected.id).isNull()

        abstractAlternativeRepository.save(clientSelected)

        Assertions.assertThat(clientSelected.id).isNotNull
    }
}