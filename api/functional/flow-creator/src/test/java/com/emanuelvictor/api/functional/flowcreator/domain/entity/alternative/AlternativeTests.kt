package com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entity.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entity.Option
import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.IntermediaryAlternative.Companion.SEPARATOR
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class AlternativeTests {

    @Test
    fun `Instance Of Intermediary Alternative Tests`() {
        val client = Option("Bubblemix Tea")
        val clientSelected = RootAlternative(client, "Selecione a unidade?")
        val unit = Option("BIG - Foz do Iguaçu")
        val unitSelected = IntermediaryAlternative(clientSelected, unit, "Por quem você foi atendido?")

        val choice = Choice(unitSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(client.name + SEPARATOR + unit.name)
    }

    @Test
    fun `Must return a long path from the choice`() {
        val client = Option("Bubblemix Tea")
        val clientSelected = RootAlternative(client, "Selecione a unidade?")
        val unit = Option("BIG - Foz do Iguaçu")
        val unitSelected = IntermediaryAlternative(clientSelected, unit, "Por quem você foi atendido?")
        val attendant = Option("Maria")
        val attendantSelected = IntermediaryAlternative(unitSelected, attendant, "Por quem você foi atendido?")

        val choice = Choice(attendantSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath())
            .isEqualTo(client.name + SEPARATOR + unit.name + SEPARATOR + attendant.name)
    }


    @Test
    fun `Instance Of Root Alternative Tests`() {
        val client = Option("Bubblemix Tea")
        val clientSelected = RootAlternative(client, "Selecione a unidade?")

        val choice = Choice(clientSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(client.name)
    }
}