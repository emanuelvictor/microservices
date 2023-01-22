package com.emanuelvictor.api.functional.flowcreator.domain.services;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Option;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
@SpringBootTest
public class AlternativeServiceTests {

    @Autowired
    private AlternativeService alternativeService;

    private IntermediaryAlternative unity1Selected;

    @BeforeEach
    public void beforeEach() {
        final RootAlternative clientSelected = alternativeService.save(new RootAlternative("Selecione a unidade?", false, new Option("Bubblemix Tea")));
        unity1Selected = alternativeService.save(new IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", true, new Option("BIG - Foz do Iguaçu")));
    }

    /**
     *
     */
    @Test
    public void mustPersistSevenCombinationsFromTrheeAlternatives() {
        final Set<IntermediaryAlternative> alternativesSaved = new HashSet<>();

        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option("Sarah"))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option("Emanuel"))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option("Israel"))));

        Assertions.assertThat(AlternativeService.generate(alternativesSaved.size()).size()).isEqualTo(alternativeService.findChildrenFromAlternativeId(unity1Selected.getId()).count());
    }

    /**
     *
     */
    @Test
    public void mustPersistThirtyOneCombinationsFromFiveAlternatives() {
        final Set<IntermediaryAlternative> alternativesSaved = new HashSet<>();
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option("Sarah"))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option("Emanuel"))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option("Israel"))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option("Tais"))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option("Francielly"))));

        Assertions.assertThat(AlternativeService.generate(alternativesSaved.size()).size()).isEqualTo(alternativeService.findChildrenFromAlternativeId(unity1Selected.getId()).count());

        alternativeService.findChildrenFromAlternativeId(unity1Selected.getId()).forEach(intermediaryAlternative -> System.out.println(Arrays.stream(intermediaryAlternative.getOptions()).map(Option::getValue).collect(Collectors.joining(","))));
    }

    @Test
    public void mustGenerateSevenCombinationsFromThreeAlternatives() {
        Set<IntermediaryAlternative> alternatives = new HashSet<>();
        final IntermediaryAlternative sarah = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option("Sarah"));
        alternatives = AlternativeService.generateAlternatives(alternatives, sarah);
        final IntermediaryAlternative emanuel = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option("Emanuel"));
        alternatives = AlternativeService.generateAlternatives(alternatives, emanuel);
        final IntermediaryAlternative israel = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option("Israel"));
        alternatives = AlternativeService.generateAlternatives(alternatives, israel);

        Assertions.assertThat(7).isEqualTo(alternatives.size());
    }

    @Test
    public void mustConvertValuesFromAlternativeToSetOfString() {
        final String sarahName = "Sarah";
        final String emanuelName = "Emanuel";
        final IntermediaryAlternative sarah = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option(sarahName));
        final IntermediaryAlternative emanuel = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option(emanuelName));
        final IntermediaryAlternative sarahEEmanuel = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option(sarahName + "," + emanuelName));
        final Stream<IntermediaryAlternative> alternatives = Stream.of(sarah, emanuel, sarahEEmanuel);

        final Set<String> values = AlternativeService.extractIsolatedValuesFromAlternatives(alternatives).collect(Collectors.toSet());

        Assertions.assertThat(values).isEqualTo(Stream.of(sarahName, emanuelName).collect(Collectors.toSet()));
    }

    @Test
    public void mustBeTheMergeBeetweenTwoListsOfAlternativesAndTheValuesFromTheFirstListMustBePreserved() {
        final IntermediaryAlternative sarah = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option("Sarah"));
        sarah.setId(1);
        final IntermediaryAlternative emanuel = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option("Emanuel"));
        emanuel.setId(2);
        final Set<IntermediaryAlternative> firstCollection = new HashSet<>();
        firstCollection.add(sarah);
        firstCollection.add(emanuel);
        final IntermediaryAlternative israel = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option("Israel"));
        final IntermediaryAlternative tais = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new Option("Tais"));
        final Set<IntermediaryAlternative> secondCollection = new HashSet<>();
        secondCollection.add(israel);
        secondCollection.add(tais);

        final Set<IntermediaryAlternative> thirdCollection = AlternativeService.mergeAlternativeLists(firstCollection, secondCollection);

        Assertions.assertThat(thirdCollection.stream().filter(alternativeFromThirdCollection -> Optional.ofNullable(alternativeFromThirdCollection.getId()).isPresent()).collect(Collectors.toSet())).isEqualTo(firstCollection);
        Assertions.assertThat(thirdCollection.stream().filter(alternativeFromThirdCollection -> Optional.ofNullable(alternativeFromThirdCollection.getId()).isEmpty()).collect(Collectors.toSet())).isEqualTo(secondCollection);
    }


}
