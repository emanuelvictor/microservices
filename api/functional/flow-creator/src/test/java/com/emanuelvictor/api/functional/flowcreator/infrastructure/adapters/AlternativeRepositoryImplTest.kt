package com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters

import com.emanuelvictor.api.functional.flowcreator.domain.entity.Option
import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.ports.AlternativeRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class AlternativeRepositoryImplTest {

    @Test
    fun `Must verify if classes is equals`() {
        val option = Option("Bubblemix Tea")
        val rootAlternative = RootAlternative(option, "whatever")

        val firstVerification = AlternativeRepositoryImpl.areTheClassesEquals(rootAlternative::class.java, RootAlternative::class.java)
        val secondVerification = AlternativeRepositoryImpl.areTheClassesEquals(rootAlternative.javaClass, RootAlternative::class.java)

        Assertions.assertThat(firstVerification).isTrue;
        Assertions.assertThat(secondVerification).isTrue;
    }
}