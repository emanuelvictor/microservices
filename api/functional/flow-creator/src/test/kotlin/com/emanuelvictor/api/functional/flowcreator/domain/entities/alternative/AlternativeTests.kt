package com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative

import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.BranchOption
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.CompanyOption
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.PersonOption
import com.emanuelvictor.api.functional.flowcreator.domain.entities.question.Question
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
    inner class AlternativeTests {

        /**
         *
         */
        @Test
        fun `Must create instance from Alternative`() {
            val messageToNext = Question("messageToNext", "messageToNext")
            val twoValues = arrayListOf(PersonOption("Value One"), PersonOption("Value Two"))
            val alternativeWithTwoValues = object : Alternative(messageToNext, true, twoValues) {
            }
            val oneValue = PersonOption("Value One")
            val alternativeWithOneValue = object : Alternative(messageToNext, true, oneValue) {
            }

            val toStringFromTwoValues = alternativeWithTwoValues.optionsValuesToString()
            val toStringFromOneValue = alternativeWithOneValue.optionsValuesToString()

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
            val unities = Question("unities", "Selecione a unidade?")
            val value = "Bubblemix Tea"
            val clientSelected = RootAlternative(unities, CompanyOption(value))
            val attendants = Question("attendants", "Por quem você foi atendido?")
            val valueFromUnit = "BIG - Foz do Iguaçu"
            val unitSelected = IntermediaryAlternative(clientSelected, attendants, BranchOption(valueFromUnit))

            val choice = Choice(unitSelected)

            Assertions.assertThat(choice).isNotNull
            Assertions.assertThat(choice.path).isNotNull
            Assertions.assertThat(choice.path).isEqualTo(value + Alternative.SEPARATOR + valueFromUnit)
        }

        /**
         *
         */
        @Test
        fun `Must return a long path from the choice`() {
            val unities = Question("unities", "Selecione a unidade?")
            val value = "Bubblemix Tea"
            val clientSelected = RootAlternative(unities, CompanyOption(value))
            val attendants = Question("attendants", "Por quem você foi atendido?")
            val valueFromUnit = "BIG - Foz do Iguaçu"
            val unitSelected = IntermediaryAlternative(clientSelected, attendants, BranchOption(valueFromUnit))
            val valueFromAttendant = "Maria"
            val attendantSelected = IntermediaryAlternative(unitSelected, attendants, PersonOption(valueFromAttendant))

            val choice = Choice(attendantSelected)

            Assertions.assertThat(choice).isNotNull
            Assertions.assertThat(choice.path).isNotNull
            Assertions.assertThat(choice.path).isEqualTo(value + Alternative.SEPARATOR + valueFromUnit + Alternative.SEPARATOR + valueFromAttendant)
        }

        /**
         *
         */
        @Test
        fun `Must compare the values of two IntermediaryAlternatives and return true`() {
            val unities = Question("unities", "Selecione a unidade?")
            val unity = RootAlternative(unities, CompanyOption("Selecione a unidade?"))
            val attendants = Question("attendants", "Como foi o atendimento?")
            val alternativeOne = IntermediaryAlternative(unity, attendants, PersonOption("Sarah"), PersonOption("Emanuel"), PersonOption("Israel"), PersonOption("Valdina"))
            val alternativeTwo = IntermediaryAlternative(unity, attendants, PersonOption("Sarah"), PersonOption("Valdina"), PersonOption("Emanuel"), PersonOption("Israel"))

            val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

            Assertions.assertThat(alternativeHaveTheSameValues).isTrue
        }

        /**
         *
         */
        @Test
        fun `Must compare the values of two IntermediaryAlternatives and return false`() {
            val unities = Question("unities", "Selecione a unidade?")
            val unity = RootAlternative(unities, CompanyOption("unity"))
            val attendants = Question("attendants", "Como foi o atendimento?")
            val alternativeOne = IntermediaryAlternative(unity, attendants, PersonOption("Sarah"), PersonOption("Emanuel"), PersonOption("Israel"), PersonOption("Jackson"))
            val alternativeTwo = IntermediaryAlternative(unity, attendants, PersonOption("Sarah"), PersonOption("Valdina"), PersonOption("Emanuel"), PersonOption("Israel"))

            val alternativeHaveTheSameValues = alternativeTwo.compareValues(alternativeOne)

            Assertions.assertThat(alternativeHaveTheSameValues).isFalse
        }

        /**
         *
         */
        @Test
        fun `Compare the values from Two IntermediaryAlternatives, with different size of values and return false`() {
            val unities = Question("unities", "Selecione a unidade?")
            val unity = RootAlternative(unities, CompanyOption("unity"))
            val attendants = Question("attendants", "Como foi o atendimento?")
            val alternativeOne = IntermediaryAlternative(unity, attendants, PersonOption("Sarah"), PersonOption("Emanuel"), PersonOption("Israel"), PersonOption("Jackson"))
            val alternativeTwo = IntermediaryAlternative(unity, attendants, PersonOption("Sarah"), PersonOption("Valdina"), PersonOption("Emanuel"), PersonOption("Israel"), PersonOption("Tais"))

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
            val unities = Question("unities", "Selecione a unidade?")
            val rootValue = "Bubblemix Tea"
            val rootAlternative = RootAlternative(unities, CompanyOption(rootValue))

            Assertions.assertThat(rootAlternative.path).isEqualTo(rootValue)
        }
    }
}