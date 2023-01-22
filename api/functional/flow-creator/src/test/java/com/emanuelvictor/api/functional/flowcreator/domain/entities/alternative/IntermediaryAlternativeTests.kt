package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.AbstractAlternative.Companion.SEPARATOR
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class IntermediaryAlternativeTests {

    @Test
    fun `Instance Of Intermediary Alternative Tests`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative( "Selecione a unidade?", value)
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected,  "Por quem você foi atendido?", valueFromUnit)

        val choice = Choice(unitSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(value + SEPARATOR + valueFromUnit)
    }

    @Test
    fun `Must return a long path from the choice`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative( "Selecione a unidade?", value)
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected,  "Por quem você foi atendido?", valueFromUnit)
        val valueFromAttendant = "Maria"
        val attendantSelected = IntermediaryAlternative(unitSelected, "Por quem você foi atendido?", valueFromAttendant)

        val choice = Choice(attendantSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(value + SEPARATOR + valueFromUnit + SEPARATOR + valueFromAttendant)
    }

    @Test
    fun `Must compare the values of two IntermediaryAlternatives and return true`() {
        val unity = RootAlternative("unity", "Selecione a unidade?")
        val alternativeOne = IntermediaryAlternative(unity, "Como foi o atendimento?", "Sarah", "Emanuel", "Israel", "Valdina")
        val alternativeTwo = IntermediaryAlternative(unity, "Como foi o atendimento?", "Sarah", "Valdina", "Emanuel", "Israel")

        val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

        Assertions.assertThat(alternativeHaveTheSameValues).isTrue
    }

    @Test
    fun `Must compare the values of two IntermediaryAlternatives and return false`() {
        val unity = RootAlternative("unity", "Selecione a unidade?")
        val alternativeOne = IntermediaryAlternative(unity, "Como foi o atendimento?", "Sarah", "Emanuel", "Israel", "Jackson")
        val alternativeTwo = IntermediaryAlternative(unity, "Como foi o atendimento?", "Sarah", "Valdina", "Emanuel", "Israel")

        val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

        Assertions.assertThat(alternativeHaveTheSameValues).isFalse
    }

    @Test
    fun `Compare the values from Two IntermediaryAlternatives, with different size of values and return false`() {
        val unity = RootAlternative("unity", "Selecione a unidade?")
        val alternativeOne = IntermediaryAlternative(unity, "Como foi o atendimento?", "Sarah", "Emanuel", "Israel", "Jackson")
        val alternativeTwo = IntermediaryAlternative(unity, "Como foi o atendimento?", "Sarah", "Valdina", "Emanuel", "Israel", "Tais")

        val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

        Assertions.assertThat(alternativeHaveTheSameValues).isFalse
    }
}