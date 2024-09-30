package com.emanuelvictor.api.functional.flowcreator.application.persistence.adapters.choice

import com.emanuelvictor.api.functional.flowcreator.application.persistence.entities.choice.ChoicePersist
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternativePersist
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternativePersist
import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.OptionPersist
import com.emanuelvictor.api.functional.flowcreator.domain.entities.question.Question
import com.emanuelvictor.api.functional.flowcreator.domain.entities.question.QuestionPersist
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

class ChoiceJpaRepositoryTests {

    private val choiceJpaRepository: ChoiceJpaRepository = ChoiceJpaRepositoryFake()

    @Test
    fun `Must convert DomainObject to PersistObject`() {
        val questionDomainObject = Question("1", "2")
        val optionDomainObject = Option("1")

        val rootAlternativeDomainObject = RootAlternative(questionDomainObject, optionDomainObject)
        val intermediaryAlternativeDomainObject =
            IntermediaryAlternative(rootAlternativeDomainObject, questionDomainObject, optionDomainObject)
        val choiceDomainObject = Choice(intermediaryAlternativeDomainObject)

        val choicePersistObject = choiceJpaRepository.domainObjectToPersistObject(choiceDomainObject)

        assertThat(choicePersistObject.date).isEqualTo(choiceDomainObject.date)
        assertThat(choicePersistObject.path).isEqualTo(choiceDomainObject.path)
        assertThat(choicePersistObject.header).isEqualTo(choiceDomainObject.header)
        assertThat(choicePersistObject.alternative.path).isEqualTo(choiceDomainObject.alternative.path)
        assertThat(choicePersistObject.alternative.question!!.name).isEqualTo(choiceDomainObject.alternative.question!!.name)
    }

    @Test
    fun `Must convert PersistObject to DomainObject`() {
        val questionPersistObject = QuestionPersist("1", "2")
        val optionPersistObject = OptionPersist("1")

        val rootAlternativePersistObject = RootAlternativePersist(questionPersistObject, optionPersistObject)
        val intermediaryAlternativePersistObject =
            IntermediaryAlternativePersist(rootAlternativePersistObject, questionPersistObject, optionPersistObject)
        val choicePersistObject = ChoicePersist(intermediaryAlternativePersistObject)

        val choiceDomainObject = choiceJpaRepository.persistObjectToDomainObject(choicePersistObject)

        assertThat(choicePersistObject.path).isEqualTo(choiceDomainObject.path)
        assertThat(choicePersistObject.header).isEqualTo(choiceDomainObject.header)
        assertThat(choicePersistObject.alternative.path).isEqualTo(choiceDomainObject.alternative.path)
        assertThat(choicePersistObject.alternative.question!!.name).isEqualTo(choiceDomainObject.alternative.question!!.name)
    }
}

class ChoiceJpaRepositoryFake : ChoiceJpaRepository(mock(ChoiceSpringDataJpaRepository::class.java))