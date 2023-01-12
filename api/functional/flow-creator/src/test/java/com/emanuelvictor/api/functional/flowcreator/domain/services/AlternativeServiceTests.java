package com.emanuelvictor.api.functional.flowcreator.domain.services;

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

    RootAlternative clientSelected;

    IntermediaryAlternative unity1Selected;

    @BeforeEach
    public void beforeEach() {
        clientSelected = alternativeService.save(new RootAlternative("Bubblemix Tea", "Selecione a unidade?", false));
        unity1Selected = alternativeService.save(new IntermediaryAlternative(clientSelected, "BIG - Foz do Iguaçu", "Por quem você foi atendido?", true));
    }

    /**
     * TODO
     */
    @Test
    public void asdfasdf() {
        System.out.println("Inserindo a Sarah:");
        final IntermediaryAlternative sarah = alternativeService.save(new IntermediaryAlternative(unity1Selected, "Sarah", "Como foi o atendimento?", false));
        System.out.println("Inserindo o Emanuel:");
        final IntermediaryAlternative emanuel = alternativeService.save(new IntermediaryAlternative(unity1Selected, "Emanuel", "Como foi o atendimento?", false));
        System.out.println("Inserindo o Israel:");
        final IntermediaryAlternative israel = alternativeService.save(new IntermediaryAlternative(unity1Selected, "Israel", "Como foi o atendimento?", false));

        System.out.println(" -------------------- ");

        alternativeService.findChildrenFromAlternativeId(unity1Selected.getId()).forEach(intermediaryAlternative -> {
            System.out.println("ALTERNATIVE ");
            System.out.println(String.join(",", intermediaryAlternative.getValues()));
        });
    }

    @Test
    public void mustGenerateSevenCombinationsFromThreeAlternatives() {
        Set<IntermediaryAlternative> alternatives = new HashSet<>();
        final IntermediaryAlternative sarah = new IntermediaryAlternative(unity1Selected, "Sarah", "Como foi o atendimento?", false);
        alternatives = AlternativeService.generateAlternatives(alternatives, sarah);
        final IntermediaryAlternative emanuel = new IntermediaryAlternative(unity1Selected, "Emanuel", "Como foi o atendimento?", false);
        alternatives = AlternativeService.generateAlternatives(alternatives, emanuel);
        final IntermediaryAlternative israel = new IntermediaryAlternative(unity1Selected, "Israel", "Como foi o atendimento?", false);
        alternatives = AlternativeService.generateAlternatives(alternatives, israel);

        Assertions.assertThat(7).isEqualTo(alternatives.size());
    }

    @Test
    public void mustConvertValuesFromAlternativeToSetOfString() {
        final String sarahName = "Sarah";
        final String emanuelName = "Emanuel";
        final IntermediaryAlternative sarah = new IntermediaryAlternative(unity1Selected, sarahName, "Como foi o atendimento?", false);
        final IntermediaryAlternative emanuel = new IntermediaryAlternative(unity1Selected, emanuelName, "Como foi o atendimento?", false);
        final IntermediaryAlternative sarahEEmanuel = new IntermediaryAlternative(unity1Selected, sarahName + "," + emanuelName, "Como foi o atendimento?", false);
        final Stream<IntermediaryAlternative> alternatives = Stream.of(sarah, emanuel, sarahEEmanuel);

        final Set<String> values = AlternativeService.extractIsolatedValuesFromAlternatives(alternatives).collect(Collectors.toSet());

        Assertions.assertThat(values).isEqualTo(Stream.of(sarahName, emanuelName).collect(Collectors.toSet()));
    }

    @Test
    public void mustBeTheMergeBeetweenTwoListsOfAlternativesAndTheValuesFromTheFirstListMustBePreserved() {
        final IntermediaryAlternative sarah = new IntermediaryAlternative(unity1Selected, "Sarah", "Como foi o atendimento?", false);
        sarah.setId(1L);
        final IntermediaryAlternative emanuel = new IntermediaryAlternative(unity1Selected, "Emanuel", "Como foi o atendimento?", false);
        emanuel.setId(2L);
        final Set<IntermediaryAlternative> firstCollection = new HashSet<>();
        firstCollection.add(sarah);
        firstCollection.add(emanuel);
        final IntermediaryAlternative israel = new IntermediaryAlternative(unity1Selected, "Israel", "Como foi o atendimento?", false);
        final IntermediaryAlternative tais = new IntermediaryAlternative(unity1Selected, "Tais", "Como foi o atendimento?", false);
        final Set<IntermediaryAlternative> secondCollection = new HashSet<>();
        secondCollection.add(israel);
        secondCollection.add(tais);

        final Set<IntermediaryAlternative> thirdCollection = AlternativeService.mergeAlternativeLists(firstCollection, secondCollection);

        Assertions.assertThat(thirdCollection.stream().filter(alternativeFromThirdCollection -> Optional.ofNullable(alternativeFromThirdCollection.getId()).isPresent()).collect(Collectors.toSet())).isEqualTo(firstCollection);
        Assertions.assertThat(thirdCollection.stream().filter(alternativeFromThirdCollection -> Optional.ofNullable(alternativeFromThirdCollection.getId()).isEmpty()).collect(Collectors.toSet())).isEqualTo(secondCollection);
    }


}
