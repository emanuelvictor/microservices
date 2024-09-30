package com.emanuelvictor.api.functional.flowcreator.infrastructure.helpers;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.Alternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.BranchOption;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.CompanyOption;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.PersonOption;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.question.Question;
import com.emanuelvictor.api.functional.flowcreator.domain.repositories.OptionRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.repositories.QuestionRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.repositories.ports.ChoiceRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.services.AlternativeService;
import com.emanuelvictor.api.functional.flowcreator.domain.services.ChoiceService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.Alternative.SEPARATOR;

/**
 *
 */
@RequiredArgsConstructor
public class PopulateHelper {
    private final ChoiceService choiceService;
    private final OptionRepository optionRepository;
    private final ChoiceRepository choiceRepository;
    private final QuestionRepository questionRepository;
    private final AlternativeService alternativeService;

    @PostConstruct
    public void postConstruct() {
//        populateBubbleMixData();
//        startProgram();
    }

    /**
     * Populate Data
     */
    public void populateBubbleMixData() {

        // Company
        final Option company = optionRepository.save(new CompanyOption("Bubblemix Tea"));
        final Question branchsQuestion = questionRepository.save(new Question("unidades", "Selecione a unidade?"));
        final RootAlternative companyAlternative = new RootAlternative(branchsQuestion, false, company);

        alternativeService.save(companyAlternative);

        // Unities
        // ---- Unity 1
        final Option firstBranch = optionRepository.save(new BranchOption("BIG - Foz do Iguaçu"));
        final Question attendantsQuestion = questionRepository.save(new Question("atendentes", "Por quem você foi atendido?"));
        final IntermediaryAlternative unity1Selected = new IntermediaryAlternative(companyAlternative, attendantsQuestion, true, firstBranch);

        alternativeService.save(unity1Selected);

        // ---- Unity 2
        final Option secondBranch = optionRepository.save(new BranchOption("Catuaí Palladium - Foz do Iguaçu"));
        final IntermediaryAlternative unity2Selected = new IntermediaryAlternative(companyAlternative, attendantsQuestion, true, secondBranch);

        alternativeService.save(unity2Selected);

        // ---- Persons of Unity 1
        // Person 1 of Unity 1
        final Option person1 = optionRepository.save(new PersonOption("Andressa"));
        final Question attendanceLevelQuestion = questionRepository.save(new Question("nível de atendimento", "Como você avalia o nível do atendimento?"));
        final IntermediaryAlternative intermediaryAlternativePerson1 = new IntermediaryAlternative(unity1Selected, attendanceLevelQuestion, false, person1);

        alternativeService.save(intermediaryAlternativePerson1);

        // Person 2 of Unity 1
        final Option person2 = optionRepository.save(new PersonOption("Marta"));
        final IntermediaryAlternative intermediaryAlternativePerson2 = new IntermediaryAlternative(unity1Selected, attendanceLevelQuestion, false, person2);

        alternativeService.save(intermediaryAlternativePerson2);

        // Person 3 of Unity 1
        final Option person3 = optionRepository.save(new PersonOption("Roberto"));
        final IntermediaryAlternative intermediaryAlternativePerson3 = new IntermediaryAlternative(unity1Selected, attendanceLevelQuestion, false, person3);

        alternativeService.save(intermediaryAlternativePerson3);

        // ---- Persons of Unity 1
        // Person 1 of Unity 2
        final Option person4 = optionRepository.save(new PersonOption("Edson"));
        final IntermediaryAlternative intermediaryAlternativePerson4 = new IntermediaryAlternative(unity2Selected, attendanceLevelQuestion, false, person4);

        alternativeService.save(intermediaryAlternativePerson4);

        // Person 2 of Unity 2
        final Option person5 = optionRepository.save(new PersonOption("Valdir"));
        final IntermediaryAlternative intermediaryAlternativePerson5 = new IntermediaryAlternative(unity2Selected, attendanceLevelQuestion, false, person5);

        alternativeService.save(intermediaryAlternativePerson5);

        // Person 3 of Unity 2
        final Option person6 = optionRepository.save(new PersonOption("Vilma"));
        final IntermediaryAlternative intermediaryAlternativePerson6 = new IntermediaryAlternative(unity2Selected, attendanceLevelQuestion, false, person6);

        alternativeService.save(intermediaryAlternativePerson6);

        alternativeService.findChildrenFromAlternativeId(companyAlternative.getId())
                .forEach(branchAlternative -> alternativeService.findChildrenFromAlternativeId(branchAlternative.getId())
                        .forEach(intermediaryAlternative -> {
                            for (int i = 1; i <= 5; i++) {
//                      TODO          var levelOption = optionRepository.listByValue(String.valueOf(i)).stream().findFirst().orElse(this.optionRepository.save(new LevelOption(i)));
//                                choiceRepository.save(new Choice(this.alternativeService.save(new IntermediaryAlternative(intermediaryAlternative, "Muito obrigado!", levelOption))));
                            }
                        }));
    }

    public void startProgram() {
        final RootAlternative rootAlternative = alternativeService.findAllRootAlternatives().stream().findFirst().orElseThrow();
        startProgram(rootAlternative.getId());
    }

    private void startProgram(final Long alternativeId) {
        final Alternative alternative = alternativeService.findById(alternativeId).orElseThrow();
        final List<IntermediaryAlternative> intermediaryAlternatives = new ArrayList<>(alternativeService.findChildrenFromAlternativeId(alternative.getId()));

        if (intermediaryAlternatives.size() == 0) {
            System.out.println(alternative.getQuestion());
            choiceService.choose((IntermediaryAlternative) alternative);

            showRaw();

            startProgram();
        }

        final HashMap<Integer, Long> alternativesMap = showAndReturnOptions(alternative, intermediaryAlternatives);

        choice(alternativesMap, alternative.getNextIsMultipleChoice());
    }

    public void showGrouped() {
        final Map<String, List<String>> choicesMap =
                choiceRepository.findAll().stream()
                        .map(choice -> Stream.of(choice.getSplittedPaths())
                                .map(HashSet::new)
                                .collect(Collectors.toSet())
                        )
                        .flatMap(sets ->
                                sets.stream().flatMap(Collection::stream)
                        )
                        .collect(Collectors.groupingBy(s -> s));
        System.out.println("----------------------------Resultados------------------------------");
        System.out.println(SEPARATOR + choiceRepository.findAll().stream().findFirst().orElseThrow().getHeader());
        choicesMap.forEach((k, v) -> System.out.println(k + " = " + v.size()));
        System.out.println("--------------------------------------------------------------------");
    }

    public void showRaw() {
        final Stream<String> paths = choiceRepository.findAll().stream()
                .map(choice -> Stream.of(choice.getSplittedPaths())
                        .map(ArrayList::new)
                        .collect(Collectors.toList())
                )
                .flatMap(arrayLists ->
                        arrayLists.stream().sorted().flatMap(Collection::stream)
                )
                .sorted();
        System.out.println("----------------------------Resultados------------------------------");
        System.out.println(SEPARATOR + choiceRepository.findAll().stream().findFirst().orElseThrow().getHeader());
        paths.forEach(System.out::println);
        System.out.println("--------------------------------------------------------------------");
    }

    public void choice(final HashMap<Integer, Long> alternativesMap, final boolean isMultipleChoice) {
//        if (isMultipleChoice) {
//            final Scanner myObj = new Scanner(System.in);
//            final int choice = Integer.parseInt(myObj.nextLine());
//            closeIfExit(choice);
//
//            final String[] choices = String.valueOf(choice).split("");
//
//            final List<Long> listAlternatives = Arrays
//                    .stream(choices)
//                    .map(choiceInt -> alternativesMap.get(Integer.valueOf(choiceInt))).collect(Collectors.toList());
//            final Long[] alternatives = Utils.getArrayFromList(listAlternatives);
//
//            startProgram(alternatives);
//        }
        final Scanner myObj = new Scanner(System.in);
        final long choice = Integer.parseInt(myObj.nextLine());

        startProgram(alternativesMap.get(choice));
    }

    private static HashMap<Integer, Long> showAndReturnOptions(final Alternative parentAlternative, final List<IntermediaryAlternative> intermediaryAlternatives) {
        System.out.println(parentAlternative.getPath());
        System.out.println("    " + parentAlternative.getQuestion());

        final HashMap<Integer, Long> alternativesMap = new HashMap<>();
        for (int i = 0; i < intermediaryAlternatives.size(); i++) {
            System.out.println(i + 1 + " - " + Objects.requireNonNull(intermediaryAlternatives.get(i).getOptions()).stream().map(Option::getIdentifier).collect(Collectors.joining(",")));
            alternativesMap.put(i + 1, intermediaryAlternatives.get(i).getId());
        }
        return alternativesMap;
    }

}
