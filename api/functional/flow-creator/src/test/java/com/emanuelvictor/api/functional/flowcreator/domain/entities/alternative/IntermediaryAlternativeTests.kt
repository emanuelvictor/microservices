package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.AbstractAlternative.Companion.SEPARATOR
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class IntermediaryAlternativeTests {

    @Test
    fun `Instance Of Intermediary Alternative Tests`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative(value, "Selecione a unidade?")
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, valueFromUnit, "Por quem você foi atendido?")

        val choice = Choice(unitSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(value + SEPARATOR + valueFromUnit)
    }

    @Test
    fun `Must return a long path from the choice`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative(value, "Selecione a unidade?")
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, valueFromUnit, "Por quem você foi atendido?")
        val valueFromAttendant = "Maria"
        val attendantSelected = IntermediaryAlternative(unitSelected, valueFromAttendant, "Por quem você foi atendido?")

        val choice = Choice(attendantSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(value + SEPARATOR + valueFromUnit + SEPARATOR + valueFromAttendant)
    }

    @Test
    fun `Must compare the values of two IntermediaryAlternatives and return true`() {
        val unity = RootAlternative("unity", "Selecione a unidade?")
        val alternativeOne = IntermediaryAlternative(unity, arrayListOf("Sarah","Emanuel", "Israel", "Valdina"), "Como foi o atendimento?", false)
        val alternativeTwo = IntermediaryAlternative(unity, arrayListOf("Sarah", "Valdina", "Emanuel", "Israel" ), "Como foi o atendimento?", false)

        val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

        Assertions.assertThat(alternativeHaveTheSameValues).isTrue
    }

    @Test
    fun `Must compare the values of two IntermediaryAlternatives and return false`() {
        val unity = RootAlternative("unity", "Selecione a unidade?")
        val alternativeOne = IntermediaryAlternative(unity, arrayListOf("Sarah","Emanuel", "Israel", "Jackson"), "Como foi o atendimento?", false)
        val alternativeTwo = IntermediaryAlternative(unity, arrayListOf("Sarah", "Valdina", "Emanuel", "Israel" ), "Como foi o atendimento?", false)

        val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

        Assertions.assertThat(alternativeHaveTheSameValues).isFalse
    }

    @Test
    fun `Compare the values from Two IntermediaryAlternatives, with different size of values and return false`() {
        val unity = RootAlternative("unity", "Selecione a unidade?")
        val alternativeOne = IntermediaryAlternative(unity, arrayListOf("Sarah","Emanuel", "Israel", "Jackson"), "Como foi o atendimento?", false)
        val alternativeTwo = IntermediaryAlternative(unity, arrayListOf("Sarah", "Valdina", "Emanuel", "Israel", "Tais" ), "Como foi o atendimento?", false)

        val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

        Assertions.assertThat(alternativeHaveTheSameValues).isFalse
    }
}