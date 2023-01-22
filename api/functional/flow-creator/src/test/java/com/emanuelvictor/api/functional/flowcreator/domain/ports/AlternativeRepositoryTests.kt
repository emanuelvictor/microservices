package com.emanuelvictor.api.functional.flowcreator.domain.ports

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Option
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.AlternativeRepository
import com.emanuelvictor.api.functional.flowcreator.infrastructure.helprs.PopulateHelper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
@SpringBootTest
class AlternativeRepositoryTests(@Autowired val alternativeRepository: AlternativeRepository, @Autowired val populateHelper: PopulateHelper) {

    /**
     *
     */
    @BeforeEach
    fun beforeEach() {
        populateHelper.eraseData()
        populateHelper.populateData()
    }

    /**
     *
     */
    @Test
    fun `Must verify if the database is populated`() {
        Assertions.assertThat(alternativeRepository.findAll().count()).isEqualTo(17)
        Assertions.assertThat(alternativeRepository.findAllRootAlternatives().count()).isEqualTo(1)
        Assertions.assertThat(alternativeRepository.findAllIntermediaryAlternatives().count()).isEqualTo(16)

        val rootAlternative = alternativeRepository.findAllRootAlternatives().findFirst().orElseThrow()
        val unities = alternativeRepository.findChildrenFromAlternativeId(rootAlternative.id)

        Assertions.assertThat(unities.count()).isEqualTo(2)
    }

    /**
     *
     */
    @Test
    fun `Must verify the children of Root`() {
        val rootAlternative = alternativeRepository.findAllRootAlternatives().findFirst().orElseThrow()
        val unities = alternativeRepository.findChildrenFromAlternativeId(rootAlternative.id)

        Assertions.assertThat(unities.count()).isEqualTo(2)
    }

    /**
     *
     */
    @Test
    fun `Must save IntermediaryAlternative`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative("Selecione a unidade?", Option(value))
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", Option(valueFromUnit))
        Assertions.assertThat(unitSelected.isSaved).isFalse

        alternativeRepository.save(unitSelected)

        Assertions.assertThat(unitSelected.isSaved).isTrue
    }

    /**
     *
     */
    @Test
    fun `Must save RootAlternative`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative("Selecione a unidade?", Option(value))
        Assertions.assertThat(clientSelected.isSaved).isFalse

        alternativeRepository.save(clientSelected)

        Assertions.assertThat(clientSelected.isSaved).isTrue
    }


}