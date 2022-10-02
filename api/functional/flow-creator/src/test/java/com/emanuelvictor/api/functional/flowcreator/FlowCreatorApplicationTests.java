package com.emanuelvictor.api.functional.flowcreator;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Alternative;
import com.emanuelvictor.api.functional.flowcreator.domain.repositories.AlternativeRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.repositories.OptionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.emanuelvictor.api.functional.flowcreator.infrastructure.helprs.PopulateHelper.ROOT_ID;

@SpringBootTest
class FlowCreatorApplicationTests {

    /**
     *
     */
    @Autowired
    private OptionRepository optionRepository;

    /**
     *
     */
    @Autowired
    private AlternativeRepository alternativeRepository;

    /**
     *
     */
    @Test
    void contextLoads() {
        Assertions.assertThat((int) optionRepository.findAll().count()).isEqualTo(9);
        Assertions.assertThat((int) alternativeRepository.findAll().count()).isEqualTo(9);

        final List<Alternative> unities = alternativeRepository.findByPreviousId(ROOT_ID);

        unities.forEach(alternative -> System.out.println(alternative.getOption().getName()));

    }

}
