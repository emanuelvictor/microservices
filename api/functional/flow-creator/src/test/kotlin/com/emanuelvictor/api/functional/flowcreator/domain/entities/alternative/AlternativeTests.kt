package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.BranchOption
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.CompanyOption
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.PersonOption
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
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
    @Nested
    inner class AbstractAlternativeTests {

        /**
         *
         */
        @Test
        fun `Must create instance from AbstractAlternative`() {
            val twoValues = arrayListOf(PersonOption("Value One"), PersonOption("Value Two"))
            val abstractAlternativeWithTwoValues = object : AbstractAlternative("messageToNext", true, twoValues) {
            }
            val oneValue = PersonOption("Value One")
            val abstractAlternativeWithOneValue = object : AbstractAlternative("messageToNext", true, oneValue) {
            }

            val toStringFromTwoValues = abstractAlternativeWithTwoValues.optionsValuesToString()
            val toStringFromOneValue = abstractAlternativeWithOneValue.optionsValuesToString()

            Assertions.assertThat(toStringFromTwoValues).isEqualTo("[Value One, Value Two]")
            Assertions.assertThat(toStringFromOneValue).isEqualTo("Value One")
        }

    }

    /**
     *
     */
    @Nested
    inner class IntermediaryAlternativeTests {

        /**
         *
         */
        @Test
        fun `Must create a instance from IntermediaryAlternative`() {
            val value = "Bubblemix Tea"
            val clientSelected = RootAlternative("Selecione a unidade?", CompanyOption(value))
            val valueFromUnit = "BIG - Foz do Iguaçu"
            val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", BranchOption(valueFromUnit))

            val choice = Choice(unitSelected)

            Assertions.assertThat(choice).isNotNull
            Assertions.assertThat(choice.path).isNotNull
            Assertions.assertThat(choice.path).isEqualTo(value + AbstractAlternative.SEPARATOR + valueFromUnit)
        }

        /**
         *
         */
        @Test
        fun `Must return a long path from the choice`() {
            val value = "Bubblemix Tea"
            val clientSelected = RootAlternative("Selecione a unidade?", CompanyOption(value))
            val valueFromUnit = "BIG - Foz do Iguaçu"
            val unitSelected = IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", BranchOption(valueFromUnit))
            val valueFromAttendant = "Maria"
            val attendantSelected = IntermediaryAlternative(unitSelected, "Por quem você foi atendido?", PersonOption(valueFromAttendant))

            val choice = Choice(attendantSelected)

            Assertions.assertThat(choice).isNotNull
            Assertions.assertThat(choice.path).isNotNull
            Assertions.assertThat(choice.path).isEqualTo(value + AbstractAlternative.SEPARATOR + valueFromUnit + AbstractAlternative.SEPARATOR + valueFromAttendant)
        }

        /**
         *
         */
        @Test
        fun `Must compare the values of two IntermediaryAlternatives and return true`() {
            val unity = RootAlternative("unity", CompanyOption("Selecione a unidade?"))
            val alternativeOne = IntermediaryAlternative(unity, "Como foi o atendimento?", PersonOption("Sarah"), PersonOption("Emanuel"), PersonOption("Israel"), PersonOption("Valdina"))
            val alternativeTwo = IntermediaryAlternative(unity, "Como foi o atendimento?", PersonOption("Sarah"), PersonOption("Valdina"), PersonOption("Emanuel"), PersonOption("Israel"))

            val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

            Assertions.assertThat(alternativeHaveTheSameValues).isTrue
        }

        /**
         *
         */
        @Test
        fun `Must compare the values of two IntermediaryAlternatives and return false`() {
            val unity = RootAlternative("Selecione a unidade?", CompanyOption("unity"))
            val alternativeOne = IntermediaryAlternative(unity, "Como foi o atendimento?", PersonOption("Sarah"), PersonOption("Emanuel"), PersonOption("Israel"), PersonOption("Jackson"))
            val alternativeTwo = IntermediaryAlternative(unity, "Como foi o atendimento?", PersonOption("Sarah"), PersonOption("Valdina"), PersonOption("Emanuel"), PersonOption("Israel"))

            val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

            Assertions.assertThat(alternativeHaveTheSameValues).isFalse
        }

        /**
         *
         */
        @Test
        fun `Compare the values from Two IntermediaryAlternatives, with different size of values and return false`() {
            val unity = RootAlternative("Selecione a unidade?", CompanyOption("unity"))
            val alternativeOne = IntermediaryAlternative(unity, "Como foi o atendimento?", PersonOption("Sarah"), PersonOption("Emanuel"), PersonOption("Israel"), PersonOption("Jackson"))
            val alternativeTwo =
                IntermediaryAlternative(unity, "Como foi o atendimento?", PersonOption("Sarah"), PersonOption("Valdina"), PersonOption("Emanuel"), PersonOption("Israel"), PersonOption("Tais"))

            val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

            Assertions.assertThat(alternativeHaveTheSameValues).isFalse
        }
    }

    /**
     *
     */
    @Nested
    inner class RootAlternativeTests {

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
}