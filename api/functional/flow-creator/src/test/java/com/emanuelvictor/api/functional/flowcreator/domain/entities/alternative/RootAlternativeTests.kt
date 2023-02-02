package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.CompanyOption
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 *
 */
class RootAlternativeTests {

    /**
     *
     */
    @Test
    fun `Instance Of Root Alternative Tests`() {
        val rootValue = "Bubblemix Tea"
        val rootAlternative = RootAlternative("Selecione a unidade?", CompanyOption(rootValue))

        Assertions.assertThat(rootAlternative.path).isEqualTo(rootValue)
    }
}