package com.emanuelvictor.api.functional.flowcreator.domain.services;

import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.ChoiceRepository;
import com.emanuelvictor.api.functional.flowcreator.infrastructure.helprs.PopulateHelper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 */
@SpringBootTest
public class ChoiceServiceTests {

    @Autowired
    private PopulateHelper populateHelper;

    @Autowired
    private ChoiceService choiceService;

    @Autowired
    private ChoiceRepository choiceRepository;

    @BeforeEach
    public void beforeEach() {
        populateHelper.eraseData();
        populateHelper.populateData();
    }

}
