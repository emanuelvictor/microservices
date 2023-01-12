package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class RootAlternativeTests {

    @Test
    fun `Instance Of Root Alternative Tests`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative(value, "Selecione a unidade?")

        val choice = Choice(clientSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(value)
    }
}