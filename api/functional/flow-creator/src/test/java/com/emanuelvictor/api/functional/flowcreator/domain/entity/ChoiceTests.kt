package com.emanuelvictor.api.functional.flowcreator.domain.entity

import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.IntermediaryAlternative.Companion.SEPARATOR
import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.RootAlternative
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ChoiceTests {

    @Test
    fun `Instance Of Choice Tests`() {
        val client = Option("Bubblemix Tea")
        val rootAlternative = RootAlternative(client, "Selecione a unidade?")

        val choice = Choice(rootAlternative)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo("Bubblemix Tea")
    }

    @Test
    fun `Must return a long path from the choice`() {
        val client = Option("Bubblemix Tea")
        val clientSelected = RootAlternative(client,"Selecione a unidade?")
        val unit = Option("BIG - Foz do Iguaçu")
        val unitSelected = IntermediaryAlternative( clientSelected,unit,"Por quem você foi atendido?")
        val attendant = Option("Maria")
        val attendantSelected = IntermediaryAlternative(unitSelected,attendant,"Por quem você foi atendido?")

        val choice = Choice(attendantSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(client.name + SEPARATOR + unit.name + SEPARATOR + attendant.name)
    }
}