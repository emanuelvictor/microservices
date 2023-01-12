package com.emanuelvictor.api.functional.flowcreator.domain.entities

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.AbstractAlternative.Companion.SEPARATOR
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ChoiceTests {

    @Test
    fun `Instance Of Choice Tests`() {
        val value = "Bubblemix Tea"
        val rootAlternative = RootAlternative(value, "Selecione a unidade?")

        val choice = Choice(rootAlternative)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo("Bubblemix Tea")
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
}