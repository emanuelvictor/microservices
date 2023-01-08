package com.emanuelvictor.api.functional.flowcreator.domain.entity

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class OptionTests {

    @Test
    fun `Instance Of Option Tests`() {
        val name = "test"

        val option = Option(name)

        Assertions.assertThat(option).isNotNull
    }
}