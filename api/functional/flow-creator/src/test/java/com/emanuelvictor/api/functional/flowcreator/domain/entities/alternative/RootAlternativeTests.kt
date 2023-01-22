package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class RootAlternativeTests {

    @Test
    fun `Instance Of Root Alternative Tests`() {
        val rootValue = "Bubblemix Tea"
        val rootAlternative = RootAlternative( "Selecione a unidade?", rootValue)

        Assertions.assertThat(rootAlternative.path).isEqualTo(rootValue)
    }
}