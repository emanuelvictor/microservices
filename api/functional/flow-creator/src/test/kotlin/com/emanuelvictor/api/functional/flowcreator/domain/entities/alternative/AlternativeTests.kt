package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.PersonOption
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
class AlternativeTests {

    /**
     *
     */
    @Test
    fun `Must convert Set to String`() {
        val twoValues = arrayListOf(PersonOption("Value One"), PersonOption("Value Two"))
        val alternativeWithTwoValues = object : Alternative("messageToNext", true, twoValues) {
            override val signature: String
                get() = ("Not yet implemented")
            override val path: String
                get() = ("Not yet implemented")
        }
        val oneValue = PersonOption("Value One")
        val alternativeWithOneValue = object : Alternative("messageToNext", true, oneValue) {
            override val signature: String
                get() = ("Not yet implemented")
            override val path: String
                get() = ("Not yet implemented")
        }

        val toStringFromTwoValues = alternativeWithTwoValues.optionsValuesToString()
        val toStringFromOneValue = alternativeWithOneValue.optionsValuesToString()

        Assertions.assertThat(toStringFromTwoValues).isEqualTo("[Value One, Value Two]")
        Assertions.assertThat(toStringFromOneValue).isEqualTo("Value One")
    }
}