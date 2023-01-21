package com.emanuelvictor.api.functional.flowcreator.domain.entities

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.AbstractAlternative.Companion.SEPARATOR
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.services.AlternativeService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.function.Consumer

class ChoiceTests {

    @Test
    fun `Instance Of Choice Tests`() {
        val rootValue = "Bubblemix Tea"
        val rootAlternative = RootAlternative(rootValue, "Selecione a unidade?")
        val intermediaryValue = "intermediary value"
        val intermediaryAlternative = IntermediaryAlternative(rootAlternative, intermediaryValue, "Whatever?")

        val choice = Choice(intermediaryAlternative)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(rootValue + SEPARATOR + intermediaryValue)
    }

    @Test
    fun `Must return a long path from the choice`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative(value, "Selecione a unidade?")
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, valueFromUnit, "Por quem você foi atendido?")
        val valueFromAttendant = "Maria"
        val attendantSelected = IntermediaryAlternative(unitSelected, valueFromAttendant, "Como foi o atendimento?")

        val choice = Choice(attendantSelected)

        Assertions.assertThat(choice).isNotNull
        Assertions.assertThat(choice.getPath()).isNotNull
        Assertions.assertThat(choice.getPath()).isEqualTo(value + SEPARATOR + valueFromUnit + SEPARATOR + valueFromAttendant)
    }

    /**
     * TODO must be get all ordered nodes, because the alternative can have multiples values
     * TODO passar values para o reticências, utilizar varar pra isso.
     * TODO AQUI É O B.O
     */
    @Test
    fun `Get all ordered nodes from parents`() {
        val value = "Bubblemix Tea"
        val clientSelected = RootAlternative(value, "Selecione a unidade?")
        val valueFromUnit = "BIG - Foz do Iguaçu"
        val unitSelected = IntermediaryAlternative(clientSelected, valueFromUnit, "Por quem você foi atendido?")
        val valueFromFirstAttendant = "Maria"
        val valueFromSecondAttendant = "Marcia"
        val valueFromThirdAttendant = "Marcelo"
        val attendantSelected = IntermediaryAlternative(unitSelected, arrayListOf(valueFromFirstAttendant, valueFromSecondAttendant, valueFromThirdAttendant) , "Selecione os sub atendentes?")
        val valueFromFirstSubAttendant = "Rafael"
        val valueFromSecondSubAttendant = "Ricardo"
        val valueFromThirdSubAttendant = "Samuel"
        val subAttendants = IntermediaryAlternative(attendantSelected, arrayListOf(valueFromFirstSubAttendant, valueFromSecondSubAttendant, valueFromThirdSubAttendant) , "Como foi o atendimento?")

        val nodes = Choice(subAttendants).getAllOrderedNodes()

        nodes.forEach(Consumer { println(it.value) })

//        Assertions.assertThat(nodes.size).isEqualTo(3)
//        Assertions.assertThat(nodes[0].messageToNext).isEqualTo("Selecione a unidade?")
//        Assertions.assertThat(nodes[1].messageToNext).isEqualTo("Por quem você foi atendido?")
//        Assertions.assertThat(nodes[2].messageToNext).isEqualTo("Como foi o atendimento?")
    }

}