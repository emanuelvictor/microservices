package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.Alternative.Companion.SEPARATOR
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.BranchOption
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.CompanyOption
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.PersonOption
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 *
 */
class IntermediaryAlternativeTests {

    /**
     *
     */
    @Test
    fun `Instance Of Intermediary Alternative Tests`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative("Selecione a unidade?", CompanyOption(value))
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", BranchOption(valueFromUnit))

        val choice = Choice(unitSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.path).isNotNull
        Assertions.assertThat(choice.path).isEqualTo(value + SEPARATOR + valueFromUnit)
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
        val attendantSelected = IntermediaryAlternative(unitSelected, "Por quem você foi atendido?", PersonOption(valueFromAttendant))

        val choice = Choice(attendantSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.path).isNotNull
        Assertions.assertThat(choice.path).isEqualTo(value + SEPARATOR + valueFromUnit + SEPARATOR + valueFromAttendant)
    }

    /**
     *
     */
    @Test
    fun `Must compare the values of two IntermediaryAlternatives and return true`() {
        val unity = RootAlternative("unity", CompanyOption("Selecione a unidade?"))
        val alternativeOne = IntermediaryAlternative(unity, "Como foi o atendimento?", PersonOption("Sarah"), PersonOption("Emanuel"), PersonOption("Israel"), PersonOption("Valdina"))
        val alternativeTwo = IntermediaryAlternative(unity, "Como foi o atendimento?", PersonOption("Sarah"), PersonOption("Valdina"), PersonOption("Emanuel"), PersonOption("Israel"))

        val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

        Assertions.assertThat(alternativeHaveTheSameValues).isTrue
    }

    /**
     *
     */
    @Test
    fun `Must compare the values of two IntermediaryAlternatives and return false`() {
        val unity = RootAlternative("Selecione a unidade?", CompanyOption("unity"))
        val alternativeOne = IntermediaryAlternative(unity, "Como foi o atendimento?", PersonOption("Sarah"), PersonOption("Emanuel"), PersonOption("Israel"), PersonOption("Jackson"))
        val alternativeTwo = IntermediaryAlternative(unity, "Como foi o atendimento?", PersonOption("Sarah"), PersonOption("Valdina"), PersonOption("Emanuel"), PersonOption("Israel"))

        val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

        Assertions.assertThat(alternativeHaveTheSameValues).isFalse
    }

    /**
     *
     */
    @Test
    fun `Compare the values from Two IntermediaryAlternatives, with different size of values and return false`() {
        val unity = RootAlternative( "Selecione a unidade?", CompanyOption("unity"))
        val alternativeOne = IntermediaryAlternative(unity, "Como foi o atendimento?", PersonOption("Sarah"), PersonOption("Emanuel"), PersonOption("Israel"), PersonOption("Jackson"))
        val alternativeTwo = IntermediaryAlternative(unity, "Como foi o atendimento?", PersonOption("Sarah"), PersonOption("Valdina"), PersonOption("Emanuel"), PersonOption("Israel"), PersonOption("Tais"))

        val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

        Assertions.assertThat(alternativeHaveTheSameValues).isFalse
    }
}