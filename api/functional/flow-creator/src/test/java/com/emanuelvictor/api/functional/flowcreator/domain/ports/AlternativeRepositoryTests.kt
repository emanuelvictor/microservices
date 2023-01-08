package com.emanuelvictor.api.functional.flowcreator.domain.ports

import com.emanuelvictor.api.functional.flowcreator.domain.entity.Option
import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.RootAlternative
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
        Assertions.assertThat(alternativeRepository.findAll().count()).isEqualTo(9)
        Assertions.assertThat(alternativeRepository.findAllRootAlternatives().count()).isEqualTo(1)
        Assertions.assertThat(alternativeRepository.findAllIntermediaryAlternatives().count()).isEqualTo(8)

        val rootAlternative = alternativeRepository.findAllRootAlternatives().findFirst().orElseThrow()
        val unities = alternativeRepository.findByPreviousId(rootAlternative.id)

        Assertions.assertThat(unities.count()).isEqualTo(2)
    }

    /**
     *
     */
    @Test
    fun `Must verify the children of Root`() {
        val rootAlternative = alternativeRepository.findAllRootAlternatives().findFirst().orElseThrow()
        val unities = alternativeRepository.findByPreviousId(rootAlternative.id)

        Assertions.assertThat(unities.count()).isEqualTo(2)
    }

    /**
     *
     */
    @Test
    fun `Must save IntermediaryAlternative`() {
        val client = Option("Bubblemix Tea")
        val clientSelected = RootAlternative(client, "Selecione a unidade?")
        val unit = Option("BIG - Foz do Iguaçu")
        val unitSelected = IntermediaryAlternative(clientSelected, unit, "Por quem você foi atendido?")
        Assertions.assertThat(unitSelected.isSaved).isFalse

        alternativeRepository.save(unitSelected)

        Assertions.assertThat(unitSelected.isSaved).isTrue
    }

    /**
     *
     */
    @Test
    fun `Must save RootAlternative`() {
        val client = Option("Bubblemix Tea")
        val clientSelected = RootAlternative(client, "Selecione a unidade?")
        Assertions.assertThat(clientSelected.isSaved).isFalse

        alternativeRepository.save(clientSelected)

        Assertions.assertThat(clientSelected.isSaved).isTrue
    }



}