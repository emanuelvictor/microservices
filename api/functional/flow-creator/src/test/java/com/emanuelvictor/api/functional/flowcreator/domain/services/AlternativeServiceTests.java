package com.emanuelvictor.api.functional.flowcreator.domain.services;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.BranchOption;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.CompanyOption;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.PersonOption;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.AlternativeRepository;
import com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters.AlternativeRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class AlternativeServiceTests {

    private final AlternativeRepository alternativeRepository = new AlternativeRepositoryImpl();

    private final AlternativeService alternativeService = new AlternativeService(alternativeRepository);

    private IntermediaryAlternative unity1Selected;

    /**
     *
     */
    @BeforeEach
    public void beforeEach() {
        final RootAlternative clientSelected = alternativeService.save(new RootAlternative("Selecione a unidade?", false, new CompanyOption("Bubblemix Tea")));
        unity1Selected = alternativeService.save(new IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", true, new BranchOption("BIG - Foz do Iguaçu")));
    }

    /**
     *
     */
    @Test
    public void mustPersistSevenCombinationsFromTrheeAlternatives() {
        final Set<IntermediaryAlternative> alternativesSaved = new HashSet<>();

        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new PersonOption("Sarah"))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new PersonOption("Emanuel"))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new PersonOption("Israel"))));

        Assertions.assertThat(AlternativeService.generateCombinations(alternativesSaved.size()).size()).isEqualTo(alternativeService.findChildrenFromAlternativeId(unity1Selected.getId()).count());
    }

    /**
     *
     */
    @Test
    public void mustPersistThirtyOneCombinationsFromFiveAlternatives() {
        final Set<IntermediaryAlternative> alternativesSaved = new HashSet<>();
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new PersonOption("Sarah"))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new PersonOption("Emanuel"))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new PersonOption("Israel"))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new PersonOption("Tais"))));
        alternativesSaved.add(alternativeService.save(new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new PersonOption("Francielly"))));

        Assertions.assertThat(AlternativeService.generateCombinations(alternativesSaved.size()).size()).isEqualTo(alternativeService.findChildrenFromAlternativeId(unity1Selected.getId()).count());

        alternativeService.findChildrenFromAlternativeId(unity1Selected.getId()).forEach(intermediaryAlternative -> System.out.println(Arrays.stream(intermediaryAlternative.getOptions()).map(Option::getIdentifier).collect(Collectors.joining(","))));
    }

    @Test
    public void mustGenerateSevenCombinationsFromThreeAlternatives() {
        Set<IntermediaryAlternative> alternatives = new HashSet<>();
        final IntermediaryAlternative sarah = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new PersonOption("Sarah"));
        alternatives = AlternativeService.generateAlternatives(alternatives, sarah);
        final IntermediaryAlternative emanuel = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new PersonOption("Emanuel"));
        alternatives = AlternativeService.generateAlternatives(alternatives, emanuel);
        final IntermediaryAlternative israel = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new PersonOption("Israel"));
        alternatives = AlternativeService.generateAlternatives(alternatives, israel);

        Assertions.assertThat(7).isEqualTo(alternatives.size());
    }

    @Test
    public void mustConvertValuesFromAlternativeToSetOfString() {
        final Option sarahOption = new PersonOption("Sarah");
        final Option emanuelOption = new PersonOption("Emanuel");
        final Option jacksonOption = new PersonOption("Jackson");
        final IntermediaryAlternative sarah = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, sarahOption);
        final IntermediaryAlternative emanuel = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, emanuelOption);
        final IntermediaryAlternative jackson = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, jacksonOption);
        final IntermediaryAlternative sarahEEmanuel = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, sarahOption, emanuelOption);
        final IntermediaryAlternative sarahEJackson = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, sarahOption, jacksonOption);
        final IntermediaryAlternative emanuelEJackson = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, emanuelOption, jacksonOption);
        final IntermediaryAlternative emanuelEJacksonESarah = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, emanuelOption, jacksonOption, sarahOption);
        final Stream<IntermediaryAlternative> alternatives = Stream.of(sarah, emanuel, jackson, sarahEEmanuel, sarahEJackson, emanuelEJackson, emanuelEJacksonESarah);

        final Set<Option> values = AlternativeService.extractIsolatedValuesFromAlternatives(alternatives).collect(Collectors.toSet());

        Assertions.assertThat(values).isEqualTo(Stream.of(sarahOption, emanuelOption, jacksonOption).collect(Collectors.toSet()));
    }

    @Test
    public void mustBeTheMergeBeetweenTwoListsOfAlternativesAndTheValuesFromTheFirstListMustBePreserved() {
        final IntermediaryAlternative sarah = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new PersonOption("Sarah"));
        sarah.setId(1);
        final IntermediaryAlternative emanuel = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new PersonOption("Emanuel"));
        emanuel.setId(2);
        final Set<IntermediaryAlternative> firstCollection = new HashSet<>();
        firstCollection.add(sarah);
        firstCollection.add(emanuel);
        final IntermediaryAlternative israel = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new PersonOption("Israel"));
        final IntermediaryAlternative tais = new IntermediaryAlternative(unity1Selected, "Como foi o atendimento?", false, new PersonOption("Tais"));
        final Set<IntermediaryAlternative> secondCollection = new HashSet<>();
        secondCollection.add(israel);
        secondCollection.add(tais);

        final Set<IntermediaryAlternative> thirdCollection = AlternativeService.mergeAlternativeLists(firstCollection, secondCollection);

        Assertions.assertThat(thirdCollection.stream().filter(alternativeFromThirdCollection -> Optional.ofNullable(alternativeFromThirdCollection.getId()).isPresent()).collect(Collectors.toSet())).isEqualTo(firstCollection);
        Assertions.assertThat(thirdCollection.stream().filter(alternativeFromThirdCollection -> Optional.ofNullable(alternativeFromThirdCollection.getId()).isEmpty()).collect(Collectors.toSet())).isEqualTo(secondCollection);
    }


}
