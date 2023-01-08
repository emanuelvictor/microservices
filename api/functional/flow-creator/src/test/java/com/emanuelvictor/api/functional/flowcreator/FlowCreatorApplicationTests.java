package com.emanuelvictor.api.functional.flowcreator;

import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.IntermediaryAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.RootAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.AlternativeRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.OptionRepository;
import com.emanuelvictor.api.functional.flowcreator.infrastructure.helprs.PopulateHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
class FlowCreatorApplicationTests {

    @Autowired
    private PopulateHelper populateHelper;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private AlternativeRepository alternativeRepository;

    /**
     *
     */
    @BeforeEach
    void beforeEach() {
        populateHelper.eraseData();
        populateHelper.populateData();
    }

    /**
     *
     */
    @Test
    void learningTests() {
        Assertions.assertThat(optionRepository.findAll().count()).isEqualTo(9);
        Assertions.assertThat(alternativeRepository.findAll().count()).isEqualTo(9);
        Assertions.assertThat(alternativeRepository.findAllRootAlternatives().count()).isEqualTo(1);
        Assertions.assertThat(alternativeRepository.findAllIntermediaryAlternatives().count()).isEqualTo(8);

        final RootAlternative rootAlternative = alternativeRepository.findAllRootAlternatives().findFirst().orElseThrow();
        final Stream<IntermediaryAlternative> unities = alternativeRepository.findByPreviousId(rootAlternative.getId());

        unities.forEach(alternative -> System.out.println(alternative.getOption().getName()));
    }

}
