package com.emanuelvictor.api.functional.flowcreator.domain.services;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.BranchOption;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.CompanyOption;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.PersonOption;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.question.Question;
import com.emanuelvictor.api.functional.flowcreator.domain.repositories.OptionRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.repositories.QuestionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
@SpringBootTest
public class AlternativeServiceTests {

    /**
     *
     */
    @Autowired
    private OptionRepository optionRepository;

    /**
     *
     */
    @Autowired
    private QuestionRepository questionRepository;

    /**
     *
     */
    @Autowired
    private AlternativeService alternativeService;

    /**
     *
     */
    private Question unitiesQuestion = new Question("unities", "Selecione a unidade?");

    /**
     *
     */
    private Question atttendantsQuestion  = new Question("attendants", "Por quem você foi atendido?");

    /**
     *
     */
    private IntermediaryAlternative firstIntermediaryAlternative;

    /**
     *
     */
    @BeforeEach
    public void beforeEach() {
        unitiesQuestion = questionRepository.save(unitiesQuestion);
        atttendantsQuestion = questionRepository.save(atttendantsQuestion);
        final RootAlternative clientSelected = (RootAlternative) alternativeService.save(new RootAlternative(unitiesQuestion, false, optionRepository.save(new CompanyOption("Bubblemix Tea"))));
        firstIntermediaryAlternative = alternativeService.save(new IntermediaryAlternative(clientSelected, atttendantsQuestion, true, optionRepository.save(new BranchOption("BIG - Foz do Iguaçu"))));
    }

    /**
     *
     */
    @Test
    @Sql({"/dataset/truncate-all-tables.sql"})
    public void mustPersistSevenCombinationsFromTrheeAlternatives() {
        final Set<IntermediaryAlternative> alternativesSaved = new HashSet<>();

        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, this.optionRepository.save(new PersonOption("Sarah")))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, this.optionRepository.save(new PersonOption("Emanuel")))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, this.optionRepository.save(new PersonOption("Israel")))));

        Assertions.assertThat(AlternativeService.generateCombinations(alternativesSaved.size()).size()).isEqualTo(alternativeService.findChildrenFromAlternativeId(firstIntermediaryAlternative.getId()).size());
    }

    /**
     *
     */
    @Test
    @Sql({"/dataset/truncate-all-tables.sql"})
    public void mustPersistThirtyOneCombinationsFromFiveAlternatives() {
        final Set<IntermediaryAlternative> alternativesSaved = new HashSet<>();
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, this.optionRepository.save(new PersonOption("Sarah")))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, this.optionRepository.save(new PersonOption("Emanuel")))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, this.optionRepository.save(new PersonOption("Israel")))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, this.optionRepository.save(new PersonOption("Tais")))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, this.optionRepository.save(new PersonOption("Francielly")))));

        Assertions.assertThat(AlternativeService.generateCombinations(alternativesSaved.size()).size()).isEqualTo(alternativeService.findChildrenFromAlternativeId(firstIntermediaryAlternative.getId()).size());

        alternativeService.findChildrenFromAlternativeId(firstIntermediaryAlternative.getId()).forEach(intermediaryAlternative -> System.out.println(intermediaryAlternative.getOptions().stream().map(Option::getIdentifier).collect(Collectors.joining(","))));
    }

    @Test
    @Sql({"/dataset/truncate-all-tables.sql"})
    public void mustGenerateSevenCombinationsFromThreeAlternatives() {
        Set<IntermediaryAlternative> alternatives = new HashSet<>();
        final IntermediaryAlternative sarah = new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, new PersonOption("Sarah"));
        alternatives = AlternativeService.generateAlternatives(alternatives, sarah);
        final IntermediaryAlternative emanuel = new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, new PersonOption("Emanuel"));
        alternatives = AlternativeService.generateAlternatives(alternatives, emanuel);
        final IntermediaryAlternative israel = new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, new PersonOption("Israel"));
        alternatives = AlternativeService.generateAlternatives(alternatives, israel);

        Assertions.assertThat(7).isEqualTo(alternatives.size());
    }

    @Test
    public void mustConvertValuesFromAlternativeToSetOfString() {
        final Option sarahOption = new PersonOption("Sarah");
        final Option emanuelOption = new PersonOption("Emanuel");
        final Option jacksonOption = new PersonOption("Jackson");
        final IntermediaryAlternative sarah = new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, sarahOption);
        final IntermediaryAlternative emanuel = new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, emanuelOption);
        final IntermediaryAlternative jackson = new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, jacksonOption);
        final IntermediaryAlternative sarahEEmanuel = new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, sarahOption, emanuelOption);
        final IntermediaryAlternative sarahEJackson = new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, sarahOption, jacksonOption);
        final IntermediaryAlternative emanuelEJackson = new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, emanuelOption, jacksonOption);
        final IntermediaryAlternative emanuelEJacksonESarah = new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, emanuelOption, jacksonOption, sarahOption);
        final Stream<IntermediaryAlternative> alternatives = Stream.of(sarah, emanuel, jackson, sarahEEmanuel, sarahEJackson, emanuelEJackson, emanuelEJacksonESarah);

        final Set<Option> values = AlternativeService.extractIsolatedValuesFromAlternatives(alternatives).collect(Collectors.toSet());

        Assertions.assertThat(values).isEqualTo(Stream.of(sarahOption, emanuelOption, jacksonOption).collect(Collectors.toSet()));
    }

    @Test
    @Sql({"/dataset/truncate-all-tables.sql"})
    public void mustBeTheMergeBeetweenTwoListsOfAlternativesAndTheValuesFromTheFirstListMustBePreserved() {
        final IntermediaryAlternative sarah = new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, new PersonOption("Sarah"));
        sarah.setId(1L);
        final IntermediaryAlternative emanuel = new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, new PersonOption("Emanuel"));
        emanuel.setId(2L);
        final Set<IntermediaryAlternative> firstCollection = new HashSet<>();
        firstCollection.add(sarah);
        firstCollection.add(emanuel);
        final IntermediaryAlternative israel = new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, new PersonOption("Israel"));
        final IntermediaryAlternative tais = new IntermediaryAlternative(firstIntermediaryAlternative, atttendantsQuestion, false, new PersonOption("Tais"));
        final Set<IntermediaryAlternative> secondCollection = new HashSet<>();
        secondCollection.add(israel);
        secondCollection.add(tais);

        final Set<IntermediaryAlternative> thirdCollection = AlternativeService.mergeAlternativeLists(firstCollection, secondCollection);

        Assertions.assertThat(thirdCollection.stream().filter(alternativeFromThirdCollection -> Optional.ofNullable(alternativeFromThirdCollection.getId()).isPresent()).collect(Collectors.toSet())).isEqualTo(firstCollection);
        Assertions.assertThat(thirdCollection.stream().filter(alternativeFromThirdCollection -> Optional.ofNullable(alternativeFromThirdCollection.getId()).isEmpty()).collect(Collectors.toSet())).isEqualTo(secondCollection);
    }
}