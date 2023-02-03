package com.emanuelvictor.api.functional.flowcreator.infrastructure.helprs;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.Alternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.*;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.ChoiceRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.OptionRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.services.AlternativeService;
import com.emanuelvictor.api.functional.flowcreator.domain.services.ChoiceService;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class PopulateHelper {

    private final ChoiceService choiceService;

    private final OptionRepository optionRepository;
    private final ChoiceRepository choiceRepository;

    private final AlternativeService alternativeService;

    public PopulateHelper(ChoiceService choiceService, OptionRepository optionRepository, ChoiceRepository choiceRepository, AlternativeService alternativeService) {
        this.choiceService = choiceService;
        this.optionRepository = optionRepository;
        this.choiceRepository = choiceRepository;
        this.alternativeService = alternativeService;
    }

    /**
     * Populate Data
     */
    public void populateData() {

        // Company
        final Option company = optionRepository.save(new CompanyOption("Bubblemix Tea"));
        final RootAlternative companyAlternative = new RootAlternative("Selecione a unidade?", false, company);

        alternativeService.save(companyAlternative);

        // Unities
        // ---- Unity 1
        final Option firstBranch = optionRepository.save(new BranchOption("BIG - Foz do Iguaçu"));
        final IntermediaryAlternative unity1Selected = new IntermediaryAlternative(companyAlternative, "Por quem você foi atendido?", true, firstBranch);

        alternativeService.save(unity1Selected);

        // ---- Unity 2
        final Option secondBranch = optionRepository.save(new BranchOption("Catuaí Palladium - Foz do Iguaçu"));
        final IntermediaryAlternative unity2Selected = new IntermediaryAlternative(companyAlternative, "Por quem você foi atendido?", true, secondBranch);

        alternativeService.save(unity2Selected);

        // ---- Persons of Unity 1
        // Person 1 of Unity 1
        final Option person1 = new PersonOption("Andressa");
        final IntermediaryAlternative intermediaryAlternativePerson1 = new IntermediaryAlternative(unity1Selected, "Como você avalia o nível do atendimento?", false, person1);

        alternativeService.save(intermediaryAlternativePerson1);

        // Person 2 of Unity 1
        final Option person2 = new PersonOption("Marta");
        final IntermediaryAlternative intermediaryAlternativePerson2 = new IntermediaryAlternative(unity1Selected, "Como você avalia o nível do atendimento?", false, person2);

        alternativeService.save(intermediaryAlternativePerson2);

        // Person 3 of Unity 1
        final Option person3 = new PersonOption("Roberto");
        final IntermediaryAlternative intermediaryAlternativePerson3 = new IntermediaryAlternative(unity1Selected, "Como você avalia o nível do atendimento?", false, person3);

        alternativeService.save(intermediaryAlternativePerson3);

        // ---- Persons of Unity 1
        // Person 1 of Unity 2
        final Option person4 = new PersonOption("Edson");
        final IntermediaryAlternative intermediaryAlternativePerson4 = new IntermediaryAlternative(unity2Selected, "Como você avalia o nível do atendimento?", false, person4);

        alternativeService.save(intermediaryAlternativePerson4);

        // Person 2 of Unity 2
        final Option person5 = new PersonOption("Valdir");
        final IntermediaryAlternative intermediaryAlternativePerson5 = new IntermediaryAlternative(unity2Selected, "Como você avalia o nível do atendimento?", false, person5);

        alternativeService.save(intermediaryAlternativePerson5);

        // Person 3 of Unity 2
        final Option person6 = new PersonOption("Vilma");
        final IntermediaryAlternative intermediaryAlternativePerson6 = new IntermediaryAlternative(unity2Selected, "Como você avalia o nível do atendimento?", false, person6);

        alternativeService.save(intermediaryAlternativePerson6);

        alternativeService.findChildrenFromAlternativeId(companyAlternative.getId()).collect(Collectors.toList()).forEach(branchAlternative -> alternativeService.findChildrenFromAlternativeId(branchAlternative.getId()).collect(Collectors.toList()).forEach(intermediaryAlternative -> {
            for (int i = 1; i <= 5; i++) {
                var levelOption = optionRepository.listByValue(String.valueOf(i)).stream().findFirst().orElse(this.optionRepository.save(new LevelOption(i)));
                this.alternativeService.save(new IntermediaryAlternative(intermediaryAlternative, "Muito obrigado!", levelOption));
            }
        }));
    }

    /**
     * Erase Data
     */
    public void eraseData() {
        alternativeService.eraseData();
    }

    public void startProgram() {
        final RootAlternative rootAlternative = alternativeService.findAllRootAlternatives().findFirst().orElseThrow();
        startProgram(rootAlternative.getId());
    }

    private void startProgram(final Integer alternativeId) {
        final Alternative alternative = alternativeService.findById(alternativeId).orElseThrow();
        final List<IntermediaryAlternative> intermediaryAlternatives = alternativeService.findChildrenFromAlternativeId(alternative.getId()).collect(Collectors.toList());

        if (intermediaryAlternatives.size() == 0) {
            System.out.println(alternative.getMessageToNext());
            choiceService.makeChoice((IntermediaryAlternative) alternative);

            final Map<String, List<String>> choicesMap =
                    choiceRepository.findAll()
                            .map(choice -> Stream.of(choice.getSplittedPaths())
                                    .map(HashSet::new)
                                    .collect(Collectors.toSet())
                            )
                            .flatMap(sets ->
                                    sets.stream().flatMap(Collection::stream)
                            )
                            .collect(Collectors.groupingBy(s -> s));
            System.out.println("----------------------------Resultados------------------------------");
            choicesMap.forEach((k, v) -> System.out.println(k + " = " + v.size()));
            System.out.println("--------------------------------------------------------------------");
            startProgram();
        }

        final HashMap<Integer, Integer> alternativesMap = showAndReturnOptions(alternative, intermediaryAlternatives);

        choice(alternativesMap, alternative.getNextIsMultipleChoice());
    }

    public void choice(final HashMap<Integer, Integer> alternativesMap, final boolean isMultipleChoice) {
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
        final int choice = Integer.parseInt(myObj.nextLine());
        closeIfExit(choice);

        startProgram(alternativesMap.get(choice));
    }

    private static void closeIfExit(int choice) {
        if (choice == 0)
            System.exit(-1);
    }

    private static HashMap<Integer, Integer> showAndReturnOptions(final Alternative parentAlternative, final List<IntermediaryAlternative> intermediaryAlternatives) {
        System.out.println(parentAlternative.getPath());
        System.out.println("    " + parentAlternative.getMessageToNext());

        final HashMap<Integer, Integer> alternativesMap = new HashMap<>();
        for (int i = 0; i < intermediaryAlternatives.size(); i++) {
            System.out.println(i + 1 + " - " + String.join(",", Arrays.stream(intermediaryAlternatives.get(i).getOptions()).map(Option::getIdentifier).collect(Collectors.toList())));
            alternativesMap.put(i + 1, intermediaryAlternatives.get(i).getId());
        }
        System.out.println("0 - Exit");
        return alternativesMap;
    }

}
