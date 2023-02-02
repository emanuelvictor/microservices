package com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.CompanyOption
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 *
 */
class AlternativeRepositoryImplTest {

    /**
     *
     */
    @Test
    fun `Must verify if classes is equals`() {
        val value = "Bubblemix Tea"
        val rootAlternative = RootAlternative("whatever", CompanyOption(value))

        val firstVerification = AlternativeRepositoryImpl.areTheClassesEquals(rootAlternative::class.java, RootAlternative::class.java)
        val secondVerification = AlternativeRepositoryImpl.areTheClassesEquals(rootAlternative.javaClass, RootAlternative::class.java)

        Assertions.assertThat(firstVerification).isTrue
        Assertions.assertThat(secondVerification).isTrue
    }
}