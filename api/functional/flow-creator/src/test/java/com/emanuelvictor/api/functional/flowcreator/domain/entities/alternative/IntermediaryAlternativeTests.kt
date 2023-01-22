package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.Option
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.AbstractAlternative.Companion.SEPARATOR
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
        val clientSelected = RootAlternative("Selecione a unidade?", Option(value))
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", Option(valueFromUnit))

        val choice = Choice(unitSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(value + SEPARATOR + valueFromUnit)
    }

    /**
     *
     */
    @Test
    fun `Must return a long path from the choice`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative("Selecione a unidade?", Option(value))
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", Option(valueFromUnit))
        val valueFromAttendant = "Maria"
        val attendantSelected = IntermediaryAlternative(unitSelected, "Por quem você foi atendido?", Option(valueFromAttendant))

        val choice = Choice(attendantSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(value + SEPARATOR + valueFromUnit + SEPARATOR + valueFromAttendant)
    }

    /**
     *
     */
    @Test
    fun `Must compare the values of two IntermediaryAlternatives and return true`() {
        val unity = RootAlternative("unity", Option("Selecione a unidade?"))
        val alternativeOne = IntermediaryAlternative(unity, "Como foi o atendimento?", Option("Sarah"), Option("Emanuel"), Option("Israel"), Option("Valdina"))
        val alternativeTwo = IntermediaryAlternative(unity, "Como foi o atendimento?", Option("Sarah"), Option("Valdina"), Option("Emanuel"), Option("Israel"))

        val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

        Assertions.assertThat(alternativeHaveTheSameValues).isTrue
    }

    /**
     *
     */
    @Test
    fun `Must compare the values of two IntermediaryAlternatives and return false`() {
        val unity = RootAlternative("Selecione a unidade?", Option("unity"))
        val alternativeOne = IntermediaryAlternative(unity, "Como foi o atendimento?", Option("Sarah"), Option("Emanuel"), Option("Israel"), Option("Jackson"))
        val alternativeTwo = IntermediaryAlternative(unity, "Como foi o atendimento?", Option("Sarah"), Option("Valdina"), Option("Emanuel"), Option("Israel"))

        val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

        Assertions.assertThat(alternativeHaveTheSameValues).isFalse
    }

    /**
     *
     */
    @Test
    fun `Compare the values from Two IntermediaryAlternatives, with different size of values and return false`() {
        val unity = RootAlternative( "Selecione a unidade?", Option("unity"))
        val alternativeOne = IntermediaryAlternative(unity, "Como foi o atendimento?", Option("Sarah"), Option("Emanuel"), Option("Israel"), Option("Jackson"))
        val alternativeTwo = IntermediaryAlternative(unity, "Como foi o atendimento?", Option("Sarah"), Option("Valdina"), Option("Emanuel"), Option("Israel"), Option("Tais"))

        val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

        Assertions.assertThat(alternativeHaveTheSameValues).isFalse
    }
}