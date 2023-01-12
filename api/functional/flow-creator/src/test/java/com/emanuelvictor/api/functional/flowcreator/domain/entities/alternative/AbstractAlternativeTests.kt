package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
class AbstractAlternativeTests {

    @Test
    fun `Must convert Set to String`() {
        val twoValues = hashSetOf("Value One", "Value Two")
        val alternativeWithTwoValues = object : AbstractAlternative(twoValues, "messageToNext", true) {
            override val path: String
                get() = TODO("Not yet implemented")
        }
        val oneValue = "Value One"
        val alternativeWithOneValue = object : AbstractAlternative(oneValue, "messageToNext", true) {
            override val path: String
                get() = TODO("Not yet implemented")
        }

        val toStringFromTwoValues = alternativeWithTwoValues.valuesToString()
        val toStringFromOneValue = alternativeWithOneValue.valuesToString()

        Assertions.assertThat(toStringFromTwoValues).isEqualTo("[Value One, Value Two]")
        Assertions.assertThat(toStringFromOneValue).isEqualTo("Value One")
    }
}